import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {HttpParams} from "@angular/common/http";
import {Observable} from 'rxjs';
import {DatatablesResponse} from "@/entites/datatables-response";
import {FlashSale} from "@/entites/flash-sale";

@Injectable({
  providedIn: 'root'
})
export class FlashSaleService {
  url = environment.globalUrl.flashSaleApi;

  constructor(private restApi: RestApiService) {
  }

  getByActiveForDatatable(tableQuery: any): Observable<DatatablesResponse> {
    return this.restApi.post(this.url + "/datatables", tableQuery);
  }

  create(flashSale: FlashSale): Observable<FlashSale> {
    return this.restApi.post(this.url, flashSale);
  }

  update(flashSale: FlashSale, productIdToAdd: number = null, productIdToRemove: number = null): Observable<FlashSale> {
    const params = new HttpParams()
      .set("productIdToAdd", productIdToAdd ? productIdToAdd.toString() : "")
      .set("productIdToRemove", productIdToRemove ? productIdToRemove.toString() : "");

    return this.restApi.put(this.url, flashSale, params);
  }

  statusToggle(flashSaleId: number) {
    return this.restApi.put(this.url + "/status-toggle/" + flashSaleId);
  }

  delete(flashSaleId: number) {
    return this.restApi.delete(this.url, flashSaleId);
  }
}
