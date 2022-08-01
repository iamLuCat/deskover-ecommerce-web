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
export class BrandService {
  private url = environment.globalUrl.brandApi;

  constructor(private restApi: RestApiService) {
  }

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

  getByActive(): Observable<Brand[]> {
    return this.restApi.get(this.url + "/actived");
  }

  getById(id: number): Observable<Brand> {
    return this.restApi.getOne(this.url, id);
  }

  create(brand: Brand): Observable<Brand> {
    return this.restApi.post(this.url, brand);
  }

  update(brand: Brand): Observable<Brand> {
    return this.restApi.put(this.url, brand);
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }
}
