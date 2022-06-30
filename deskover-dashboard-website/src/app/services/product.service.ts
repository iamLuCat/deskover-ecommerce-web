import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "@/entites/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private url = environment.globalUrl.productApi;

  constructor(private restApi: RestApiService) {
  }

  getByActiveForDatatable(tableQuery: any, isActive: boolean): Promise<DataTablesResponse> {
    const params = new HttpParams().set("isActive", isActive.toString());
    return this.restApi.postWithParams(this.url + "/datatables", tableQuery, params).toPromise();
  }

  getAll(page: number, size: number, active: Boolean): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('active', active.toString());

    return this.restApi.getWithParams(this.url, params);
  }

  getByActive(): Observable<Product[]> {
    return this.restApi.get(this.url + "/actived");
  }

  getById(id: number): Observable<Product> {
    return this.restApi.getOne(this.url, id);
  }

  create(product: Product): Observable<Product> {
    return this.restApi.post(this.url, product);
  }

  update(product: Product): Observable<Product> {
    return this.restApi.put(this.url, product);
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
