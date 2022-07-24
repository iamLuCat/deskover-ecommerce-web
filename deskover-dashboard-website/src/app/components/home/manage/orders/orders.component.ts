import { Component, OnInit } from '@angular/core';
import {Order} from "@/entites/order";
import {OrderStatus} from "@/entites/order-status";
import {OrderService} from "@services/order.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {
  orders: Order[];
  order: Order = null;

  orderStatuses: OrderStatus[];
  orderStatus: OrderStatus = null;

  orderStatusCode: string = null;

  dtOptions: any = {};

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
      lengthMenu: [5, 10, 25, 50, 100],
      responsive: true,
      serverSide: true,
      processing: true,
      stateSave: true,
      ajax: (dataTablesParameters: any, callback) => {
        const params = new HttpParams();
        params.set('statusCode', this.orderStatusCode ? this.orderStatusCode : '');
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
        { data: 'orderCode'},
        { data: 'user.fullname'},
        { data: 'orderDetail.tel'},
        { data: 'orderDetail.address'},
      ]
    }
  }

  getOrderStatuses(): void {
    this.orderService.getOrderStatuses().subscribe(data => {
      console.log(data);
      this.orderStatuses = data;
    });
  }

}
