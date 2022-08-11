import {Injectable} from '@angular/core';
import {RestApiService} from "@services/rest-api.service";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {GeneralReport, OrderReport, ProductReport} from "@/entites/statistical";
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  url = environment.globalUrl.dashboardApi

  constructor(private restApiService: RestApiService) { }

  getNumberOrOrders(): Observable<GeneralReport> {
    return this.restApiService.get(this.url + '/general-report');
  }

  getTotalQuantityProduct(): Observable<OrderReport[]> {
    return this.restApiService.get(this.url + '/product-report');
  }

  topProductSold(limit?: number): Observable<ProductReport[]> {
    const params = new HttpParams().set('limit', limit.toString());
    return this.restApiService.get(this.url + '/top-product-sold', );
  }

  getSalesByOrder(month: number, year: number): Observable<any> {
    const params = new HttpParams()
      .set('month', month.toString())
      .set('year', year.toString());
    return this.restApiService.get(this.url + '/total-order-revenue', params);
  }
}
