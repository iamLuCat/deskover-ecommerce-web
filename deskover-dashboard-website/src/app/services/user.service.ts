import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Brand} from "@/entites/brand";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url = environment.globalUrl.userApi;

  constructor(private restApi: RestApiService) {
  }

  getByActiveForDatatable(tableQuery: any, isActive: boolean): Observable<DataTablesResponse> {
    const params = new HttpParams().set("isActive", isActive.toString());
    return this.restApi.postWithParams(this.url + "/datatables", tableQuery, params);
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
