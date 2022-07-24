import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {Observable} from "rxjs";
import {Subcategory} from "@/entites/subcategory";
import {CategoryService} from "@services/category.service";
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {
  private url = environment.globalUrl.subcategoryApi;

  constructor(private restApi: RestApiService, private categoryService: CategoryService) { }

  getByActiveForDatatable(tableQuery: any, isActive: boolean, categoryId: number): Observable<DataTablesResponse> {
    const params = new HttpParams()
      .set("isActive", isActive.toString())
      .set("categoryId", categoryId ? categoryId.toString() : '');
    return this.restApi.post(this.url + "/datatables", tableQuery, params);
  }

  getOne(id: number): Observable<Subcategory> {
    return this.restApi.getOne(this.url, id);
  }

  getByActive(isActive: boolean, categoryId: number): Observable<Subcategory[]> {
    const params = new HttpParams()
      .set("isActive", isActive.toString())
      .set("categoryId", categoryId ? categoryId.toString() : '');
    return this.restApi.get(this.url, params);
  }

  create(subcategory: Subcategory): Observable<Subcategory> {
    return this.restApi.post(this.url, subcategory);
  }

  update(subcategory: Subcategory): Observable<Subcategory> {
    return this.restApi.put(this.url, subcategory);
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
