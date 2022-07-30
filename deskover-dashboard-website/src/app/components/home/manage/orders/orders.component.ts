import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Order, OrderStatus} from "@/entites/order";
import {OrderService} from "@services/order.service";
import {HttpParams} from "@angular/common/http";
import {DataTableDirective} from "angular-datatables";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {environment} from "../../../../../environments/environment";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {Loading} from "notiflix";

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
        {data: 'createdAt'},
        {data: 'modifiedBy'},
        {data: 'orderStatus.status'},
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

  refreshOrderTable() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.draw();
    });
  }

  setBackgroundByStatus(statusCode: string) {
    if (statusCode.includes('-TC')) {
      return 'bg-opacity-50 text-dark bg-success';
    } else if (statusCode.includes('-TB')) {
      return 'bg-opacity-50 text-dark bg-danger';
    } else if (statusCode.includes('C-')) {
      if (statusCode.includes('C-XN')) {
        return 'bg-opacity-50 text-dark bg-warning';
      }
      return 'bg-opacity-50 text-dark bg-secondary';
    } else {
      return 'bg-opacity-50 text-dark bg-info';
    }
  }

  openProductPage(productSlug: string) {
    window.open(`${environment.globalUrl.productItem}?p=${productSlug}`, '_blank');
  }

  getOrder(order: Order) {
    this.order = order;
    this.openModal(this.orderDetailModal);
  }

  getQrCode(qrCode) {
    return `${environment.globalUrl.qrCode}/${qrCode}`;
  }

  isPendingOrder(statusCode: string) {
    if (statusCode) {
      return statusCode.includes('C-XN');
    }
  }

  changeOrderStatus(order: Order, message: string) {
    this.orderService.changeOrderStatus(order.orderCode).subscribe({
      next: (order) => {
        NotiflixUtils.successNotify(message);
        this.order = order;
        this.refreshOrderTable();
        this.closeModal();
      }
    });
  }

  confirmOrder(order: Order) {
    if (order.shipping.shippingId !== 'DKV') {
      this.orderService.confirmOrder(order).subscribe({
        next: (data) => {
          this.changeOrderStatus(order, "Xác nhận đơn hàng thành công. Đơn vị vận chuyển: " + order.shipping.name_shipping);
        }
      });
    } else {
      this.changeOrderStatus(order, "Xác nhận đơn hàng thành công. Đơn vị vận chuyển: " + order.shipping.name_shipping);
    }
  }

  cancelOrder(order: Order) {
    this.orderService.cancelOrder(order.label).subscribe({
      next: (data) => {
        this.changeOrderStatus(order, "Huỷ đơn hàng thành công");
      }
    });
  }
}
