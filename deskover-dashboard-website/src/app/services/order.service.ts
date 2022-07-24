import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  url = environment.globalUrl.orderApi;

  constructor(private restApi: RestApiService) { }

  getOrdersForDatatables(params: any) {
    return this.restApi.get(this.url + '/datatables', params);
  }
}
