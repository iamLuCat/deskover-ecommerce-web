import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Order} from "@/entites/order";
import {OrderStatus} from "@/entites/order-status";
import {OrderService} from "@services/order.service";
import {HttpParams} from "@angular/common/http";
import {DataTableDirective} from "angular-datatables";
import {BsModalRef, BsModalService, ModalDirective} from "ngx-bootstrap/modal";
import {environment} from "../../../../../environments/environment";

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
        {data: 'orderCode'},
        {data: 'fullName'},
        {data: 'orderDetail.address'},
        {data: 'createdAt'},
        {data: 'modifiedBy'},
        {data: 'orderStatus.status'},
        {data: null, orderable: false, searchable: false}
      ],
      order: [[6, 'asc']]
    }
  }

  getOrderStatuses(): void {
    this.orderService.getOrderStatuses().subscribe(data => {
      this.orderStatuses = data;
    });
  }

  applyFilter() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.draw();
    });
  }

  setBackgroundByStatus(statusCode: string) {
    if (statusCode.includes('-TC')) {
      return 'bg-success';
    } else if (statusCode.includes('-TB')) {
      return 'bg-danger';
    } else if (statusCode.includes('C-')) {
      return 'bg-warning';
    } else {
      return 'bg-info';
    }
  }

  isPending(statusCode: string) {
    if(statusCode) {
      return statusCode.includes('C-');
    }
  }

  getUrlProductClient(productSlug: any) {
    return `${environment.globalUrl.productItem}?p=${productSlug}`;
  }

  /* Order */
  getOrder(order: Order) {
    this.order = order;
    this.openModal(this.orderDetailModal);
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(
      template,{
        class: 'modal-xl modal-dialog-centered modal-dialog-scrollable',
        backdrop: 'static',
      },
    );
  }

  closeModal() {
    this.modalRef.hide();
  }
}
