import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {OrderStatus} from "@/entites/order-status";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  url = environment.globalUrl.orderApi;

  constructor(private restApi: RestApiService) { }

  getOrdersForDatatables(tableQuery: any, params: HttpParams): Observable<DataTablesResponse> {
    return this.restApi.post(this.url + '/datatables', tableQuery, params);
  }

  getOrderStatuses(): Observable<OrderStatus[]> {
    return this.restApi.get(this.url + '/statuses');
  }
}
