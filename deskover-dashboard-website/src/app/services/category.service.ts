import {Injectable} from '@angular/core';
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {environment} from "../../environments/environment";
import {Category} from "@/entites/category";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private url = environment.globalUrl.categoryApi;

  constructor(private restApi: RestApiService) {
  }

  getAllForDatatable(tableQuery: any): Promise<DataTablesResponse> {
    return this.restApi.post(this.url + "/datatables", tableQuery).toPromise();
  }

  getOne(id: number): Observable<Category> {
    return this.restApi.getOne(this.url, id);
  }

  create(category: Category): Observable<Category> {
    return this.restApi.post(this.url, category);
  }

  update(category: Category): Observable<Category> {
    return this.restApi.put(this.url, category);
  }

  delete(id: number) {
    return this.restApi.delete(this.url, id);
  }

}
