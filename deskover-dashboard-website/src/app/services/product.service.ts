import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {DatatablesResponse} from "@/entites/datatables-response";
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

  getByActiveForDatatable(tableQuery: any, params: HttpParams): Observable<DatatablesResponse> {
    return this.restApi.post(this.url + "/datatables", tableQuery, params);
  }

  getByActive(): Observable<Product[]> {
    return this.restApi.get(this.url + "/actived");
  }

  getById(id: number): Observable<Product> {
    return this.restApi.getOne(this.url, id);
  }

  create(product: Product, params: HttpParams = null): Observable<Product> {
    return this.restApi.post(this.url, product, params);
  }

  update(product: Product, params: HttpParams = null): Observable<Product> {
    return this.restApi.put(this.url, product, params);
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
