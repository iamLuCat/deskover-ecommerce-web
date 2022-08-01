import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {HttpParams} from "@angular/common/http";
import { Observable } from 'rxjs';
import {Discount} from "@/entites/discount";
import {DatatablesResponse} from "@/entites/datatables-response";

@Injectable({
  providedIn: 'root'
})
export class DiscountService {
  url = environment.globalUrl.discountApi;

  constructor(private restApi: RestApiService) { }

    getByActiveForDatatable(tableQuery: any, isActive: boolean): Observable<DatatablesResponse> {
        const params = new HttpParams().set("isActive", isActive.toString());
        return this.restApi.post(this.url + "/datatables", tableQuery, params);
    }

    getAll(page: number, size: number, active: Boolean): Observable<any> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', size.toString())
            .set('active', active.toString());

        return this.restApi.get(this.url, params);
    }

    getByActive(): Observable<Discount[]> {
        return this.restApi.get(this.url + "/actived");
    }

    getById(id: number): Observable<Discount> {
        return this.restApi.getOne(this.url, id);
    }

    create(discount: Discount): Observable<Discount> {
        return this.restApi.post(this.url, discount);
    }

    update(discount: Discount, productIdToAdd: number = null, productIdToRemove: number = null): Observable<Discount> {
        const params = new HttpParams()
          .set("productIdToAdd", productIdToAdd ? productIdToAdd.toString() : "")
          .set("productIdToRemove", productIdToRemove ? productIdToRemove.toString() : "");

        return this.restApi.put(this.url, discount, params);
    }

    changeActive(id: number) {
        return this.restApi.delete(this.url, id);
    }

}
