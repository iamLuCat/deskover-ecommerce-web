import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Order, OrderStatus} from "@/entites/order";
import {OrderService} from "@services/order.service";
import {HttpParams} from "@angular/common/http";
import {DataTableDirective} from "angular-datatables";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {environment} from "../../../../../environments/environment";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {AuthService} from "@services/auth.service";
import {PermissionContants} from "@/constants/permission-contants";
import {ActivatedRoute} from "@angular/router";
import {OrderContants} from "@/constants/order-contants";
import {lastValueFrom} from "rxjs";

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

  numberOfPendingConfirmOrder: number = 0;
  numberOfCanceledOrder: number = 0;
  numberOfPendingCancelOrder: number = 0;

  dtOptions: any = {};

  modalRef?: BsModalRef;

  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;
  @ViewChild('orderDetailModal', {static: false}) orderDetailModal: TemplateRef<any>;
  @ViewChild('imageModal', {static: false}) imageModal: TemplateRef<any>;

  constructor(
    private orderService: OrderService,
    private modalService: BsModalService,
    private authService: AuthService,
    private router: ActivatedRoute
  ) {
    this.orderStatusCode = this.router.snapshot.params['statusCode'] || null;
    this.getOrderStatuses();
    this.countOrderByStatus();
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

  async countOrderByStatus() {
    this.numberOfPendingConfirmOrder = await lastValueFrom(this.orderService.countByStatus("C-XN"));
    this.numberOfPendingCancelOrder = await lastValueFrom(this.orderService.countByStatus("C-HUY"));
    this.numberOfCanceledOrder = await lastValueFrom(this.orderService.countByStatus("HUY"));
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(
      template, {
        class: 'modal-xl modal-dialog-centered modal-dialog-scrollable',
        backdrop: 'static',
      },
    );
  }

  openImageModal(template: TemplateRef<any>, order: Order) {
    this.modalRef = this.modalService.show(
      template, {
        class: 'modal-md modal-dialog-centered modal-dialog-scrollable',
      },
    );
    this.order = Object.assign({}, order);
  }

  closeModal() {
    this.modalRef?.hide();
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

  getClassesByOrder(order: Order) {
    if (order.orderStatus?.code.includes('-TC')) {
      return 'bg-faded-success text-success';
    } else if (order.orderStatus?.code.includes('-TB')) {
      return 'bg-faded-danger text-danger';
    } else if (order.orderStatus?.code.includes('C-')) {
      if (order.orderStatus?.code.includes(OrderContants.PENDING_CONFIRM)) {
        return 'bg-faded-warning text-warning';
      }
      return 'bg-faded-secondary text-secondary';
    } else {
      return 'bg-faded-primary text-primary';
    }
  }

  getClassesByPayment(order: Order) {
    if (order.statusPayment?.code === OrderContants.PAID) {
      return 'text-primary';
    } else if (order.statusPayment?.code === OrderContants.UNPAID) {
      if (order.orderStatus?.code === OrderContants.CANCELED) {
        return 'text-primary';
      }
      return 'text-danger';
    } else if (order.statusPayment?.code === OrderContants.NOT_REFUNDED) {
      if (order.orderStatus?.code === OrderContants.CANCELED) {
        return 'text-primary';
      }
      return 'text-warning';
    } else if (order.statusPayment?.code === OrderContants.REFUNDED) {
      return 'text-secondary';
    }
  }

  openProductPage(productSlug: string) {
    window.open(`${environment.globalUrl.productItemPage}?p=${productSlug}`, '_blank');
  }

  getOrder(order: Order) {
    this.order = Object.assign({}, order);
    this.order.email = this.order.email ? this.order.email : '';
    this.openModal(this.orderDetailModal);
  }

  getQrCode(qrCode) {
    return `${environment.globalUrl.qrCode}/${qrCode}`;
  }


  isPendingConfirmOrder(order: Order) {
    return order?.orderStatus?.code === OrderContants.PENDING_CONFIRM;
  }

  isPendingCancelOrder(orderStatusCode: string) {
    return orderStatusCode === OrderContants.PENDING_CANCEL;
  }

  isCanceledOrder(orderStatusCode: string) {
    return orderStatusCode === OrderContants.CANCELED;
  }

  isCompletedOrder(orderStatusCode: string) {
    return orderStatusCode === OrderContants.DELIVERED;
  }

  isNotRefundedOrder(order: Order) {
    return order?.statusPayment?.code === OrderContants.NOT_REFUNDED
      && order.orderStatus?.code === OrderContants.CANCELED;
  }

  changeOrderStatus(order: Order, message: string) {
    this.orderService.changeOrderStatus(order.orderCode).subscribe({
      next: (order) => {
        NotiflixUtils.successNotify(message);
        NotiflixUtils.removeLoading();

        /*this.order = order;
        this.orderStatusCode = 'C-LH';*/

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
        this.closeModal();
        this.changeOrderStatus(order, "Xác nhận đơn hàng thành công. Đơn vị vận chuyển: "
          + order.shipping.name_shipping);
      } else {
        this.orderService.confirmOrder(order).subscribe({
          next: (data) => {
            NotiflixUtils.removeLoading();
            this.closeModal();
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
      this.orderService.cancelOrder(order.orderCode).subscribe({
        next: (data) => {
          NotiflixUtils.removeLoading();
          NotiflixUtils.successNotify("Hủy đơn hàng thành công");
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
      this.orderService.refundOrder(order.orderCode).subscribe({
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

  getProvince(address: string): string {
    return address.split(',')[2].trim();
  }

  hasRole() {
    return this.authService.hasPermissions([
      PermissionContants.ADMIN,
      PermissionContants.MANAGER,
    ]);
  }
}
