import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {DatatablesResponse} from "@/entites/datatables-response";
import {HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Brand} from "@/entites/brand";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private url = environment.globalUrl.customerApi;

  constructor(private restApi: RestApiService) {
  }

  getByActiveForDatatable(tableQuery: any, isActive: boolean): Observable<DatatablesResponse> {
    const params = new HttpParams().set("isActive", isActive.toString());
    return this.restApi.post(this.url + "/datatables", tableQuery, params);
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
