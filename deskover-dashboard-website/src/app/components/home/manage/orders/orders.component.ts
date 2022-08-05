import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Order, OrderStatus} from "@/entites/order";
import {OrderService} from "@services/order.service";
import {HttpParams} from "@angular/common/http";
import {DataTableDirective} from "angular-datatables";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {environment} from "../../../../../environments/environment";
import {NotiflixUtils} from "@/utils/notiflix-utils";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {
  orders: Order[];
  order: Order = null;

  orderStatuses: OrderStatus[];
  orderStatusCode: string = null;

  dtOptions: any = {};

  modalRef?: BsModalRef;

  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;
  @ViewChild('orderDetailModal', {static: false}) orderDetailModal: TemplateRef<any>;

  constructor(private orderService: OrderService, private modalService: BsModalService) {
    this.getOrderStatuses();
  }

  ngOnInit(): void {
    const self = this;

    this.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      serverSide: true,
      processing: true,
      stateSave: true,
      ajax: (dataTablesParameters: any, callback) => {
        const params = new HttpParams().set('statusCode', this.orderStatusCode ? this.orderStatusCode : '');
        this.orderService.getOrdersForDatatables(dataTablesParameters, params).subscribe(resp => {
          self.orders = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'qrCode'},
        {data: 'orderCode'},
        {data: 'fullName'},
        {data: 'orderDetail.address'},
        {data: 'unitPrice'},
        {data: 'createdAt'},
        {data: 'orderStatus.status'},
        {data: 'payment.name_payment'},
        {data: null, orderable: false, searchable: false}
      ],
      order: [[6, 'asc']],
    }
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(
      template, {
        class: 'modal-xl modal-dialog-centered modal-dialog-scrollable',
        backdrop: 'static',
      },
    );
  }

  closeModal() {
    this.modalRef.hide();
  }

  getOrderStatuses(): void {
    this.orderService.getOrderStatuses().subscribe(data => {
      this.orderStatuses = data;
    });
  }

  rerender() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.draw();
    });
  }

  getClassesByOrder(statusCode: string) {
    if (statusCode.includes('-TC')) {
      return 'bg-success';
    } else if (statusCode.includes('-TB')) {
      return 'bg-danger';
    } else if (statusCode.includes('C-')) {
      if (statusCode.includes('C-XN')) {
        return 'bg-warning';
      }
      return 'bg-secondary';
    } else {
      return 'bg-info';
    }
  }

  getClassesByPayment(paymentCode: string) {
    if (paymentCode === 'C-TT') {
      return 'text-danger';
    } else if (paymentCode === 'D-TT') {
      return 'text-success';
    } else if (paymentCode === 'C-HT') {
      return 'text-warning';
    } else if (paymentCode === 'D-HT') {
      return 'text-info';
    }
  }

  openProductPage(productSlug: string) {
    window.open(`${environment.globalUrl.productItemPage}?p=${productSlug}`, '_blank');
  }

  getOrder(order: Order) {
    this.order = order;
    this.openModal(this.orderDetailModal);
  }

  getQrCode(qrCode) {
    return `${environment.globalUrl.qrCode}/${qrCode}`;
  }

  isPendingOrder(order: Order) {
      return order.orderStatus?.code === 'C-XN';
  }

  isUnpaidOrder(order: Order) {
    return order.statusPayment?.code === 'C-TT' && order.orderStatus?.code === 'HUY';
  }

  changeOrderStatus(order: Order, message: string) {
    this.orderService.changeOrderStatus(order.orderCode).subscribe({
      next: (order) => {
        NotiflixUtils.successNotify(message);
        NotiflixUtils.removeLoading();

        this.order = order;
        this.orderStatusCode = 'C-LH';

        this.rerender();
      },
      error: () => {
        NotiflixUtils.removeLoading();
      }
    });
  }

  confirmOrder(order: Order) {
    NotiflixUtils.showConfirm('Xác nhận', 'Duyệt đơn hàng ' + order.orderCode, () => {
      NotiflixUtils.showLoading();
      if (order.shipping.shippingId === 'DKV') {
        this.changeOrderStatus(order, "Xác nhận đơn hàng thành công. Đơn vị vận chuyển: "
          + order.shipping.name_shipping);
      } else {
        this.orderService.confirmOrder(order).subscribe({
          next: (data) => {
            NotiflixUtils.removeLoading();
            this.changeOrderStatus(order, "Xác nhận đơn hàng thành công. Đơn vị vận chuyển: "
              + order.shipping.name_shipping);
          },
          error: () => {
            NotiflixUtils.removeLoading();
          }
        });
      }
    });
  }

  cancelOrder(order: Order) {
    NotiflixUtils.showConfirm('Xác nhận', 'Huỷ đơn ' + order.orderCode, () => {
      NotiflixUtils.showLoading();
      this.orderService.cancelOrder(order).subscribe({
        next: (data) => {
          NotiflixUtils.removeLoading();
          NotiflixUtils.successNotify("Hủy đơn hàng thành công");
          this.orderStatusCode = 'HUY';
          this.rerender();
        },
        error: () => {
          NotiflixUtils.removeLoading();
        }
      });
    });
  }

  refundOrder(order: Order) {
    NotiflixUtils.showConfirm('Xác nhận', 'Hoàn tiền đơn ' + order.orderCode, () => {
      NotiflixUtils.showLoading();
      this.orderService.cancelOrder(order).subscribe({
        next: (data) => {
          NotiflixUtils.removeLoading();
          NotiflixUtils.successNotify("Hoàn tiền thành công");
          this.rerender();
        },
        error: () => {
          NotiflixUtils.removeLoading();
        }
      });
    });
  }
}
