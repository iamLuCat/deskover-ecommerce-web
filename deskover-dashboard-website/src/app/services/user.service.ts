import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {DatatablesResponse} from "@/entites/datatables-response";
import {HttpParams} from "@angular/common/http";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url = environment.globalUrl.userApi;

  constructor(private restApi: RestApiService) { }

  getByActiveForDatatable(tableQuery: any, params: HttpParams): Observable<DatatablesResponse> {
    return this.restApi.post(this.url + "/datatables", tableQuery, params);
  }

  getRoles(): Observable<any> {
    return this.restApi.get(this.url + "/roles");
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
