import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {DatatablesResponse} from "@/entites/datatables-response";
import {Order, OrderStatus} from "@/entites/order";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  url = environment.globalUrl.orderApi;

  constructor(private restApi: RestApiService) { }

  getOrdersForDatatables(tableQuery: any, params: HttpParams): Observable<DatatablesResponse> {
    return this.restApi.post(this.url + '/datatables', tableQuery, params);
  }

  getOrderStatuses(): Observable<OrderStatus[]> {
    return this.restApi.get(this.url + '/statuses');
  }

  getOrder(id: number): Observable<Order> {
    return this.restApi.get(this.url + '/' + id);
  }
}
