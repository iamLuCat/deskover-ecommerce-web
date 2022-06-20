import {Injectable} from '@angular/core';
import {RestApiService} from "@services/rest-api.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Category} from "@/entites/category";
import {HttpParams} from "@angular/common/http";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {retry} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  url = environment.globalUrl.categoryApi;

  constructor(
    private restApiService: RestApiService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  getAll(page: number, size: number, isActive: Boolean): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('isActive', isActive.toString());
    return this.restApiService.getWithParams(this.url, params);
  }

  getAllForDatatables(tableQuery: any): Promise<DataTablesResponse> {
    return this.restApiService.post(this.url + '/datatables', tableQuery).toPromise();
  }

  deleteById(id: number) {
    return this.restApiService.delete(this.url, id).toPromise();
  }

}
