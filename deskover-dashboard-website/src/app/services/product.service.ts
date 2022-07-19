import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "@/entites/product";
import {UploadedImage} from "@/entites/uploaded-image";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private url = environment.globalUrl.productApi;

  constructor(private restApi: RestApiService) {
  }

  getByActiveForDatatable(tableQuery: any, isActive: boolean, categoryId: number = null, brandId: number = null): Promise<DataTablesResponse> {
    const params = new HttpParams()
      .set("isActive", isActive.toString())
      .set("categoryId", categoryId ? categoryId.toString() : '')
      .set("brandId", brandId ? brandId.toString() : '');
    return this.restApi.postWithParams(this.url + "/datatables", tableQuery, params).toPromise();
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

  uploadImage(file: File): Observable<UploadedImage> {
    const formData = new FormData();
    formData.append('file', file);
    return this.restApi.postWithFile(environment.globalUrl.uploadProductImageApi, formData);
  }
}
