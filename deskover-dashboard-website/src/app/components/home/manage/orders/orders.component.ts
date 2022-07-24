import {Component, OnInit, ViewChild} from '@angular/core';
import {Order} from "@/entites/order";
import {OrderStatus} from "@/entites/order-status";
import {OrderService} from "@services/order.service";
import {HttpParams} from "@angular/common/http";
import {DataTableDirective} from "angular-datatables";

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

  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(private orderService: OrderService) {
    this.getOrderStatuses();
  }

  ngOnInit(): void {
    const self = this;

    this.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: true,
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
        {data: 'orderDetail.tel'},
        {data: 'orderDetail.address'},
        {data: 'createdAt'},
        {data: 'modifiedBy'},
      ]
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
}
