import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {Observable} from "rxjs";
import {Subcategory} from "@/entites/subcategory";

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {
  private url = environment.globalUrl.subcategoryApi;

  constructor(private restApi: RestApiService) { }

  getAllForDatatable(tableQuery: any): Promise<DataTablesResponse> {
    return this.restApi.post(this.url + "/datatables", tableQuery).toPromise();
  }

  getOne(id: number): Observable<Subcategory> {
    return this.restApi.getOne(this.url, id);
  }

  create(subcategory: Subcategory): Observable<Subcategory> {
    return this.restApi.post(this.url, subcategory);
  }

  update(subcategory: Subcategory): Observable<Subcategory> {
    return this.restApi.put(this.url, subcategory);
  }

  delete(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
