import {Injectable} from '@angular/core';
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {environment} from "../../environments/environment";
import {Category} from "@/entites/category";
import {Observable} from "rxjs";
import {HttpParams} from "@angular/common/http";

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

  getAll(page: number, size: number, active: Boolean): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('active', active.toString());

    return this.restApi.getWithParams(this.url, params);
  }

  getAllByActived(): Observable<Category[]> {
    return this.restApi.get(this.url + "/actived");
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

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }

}
