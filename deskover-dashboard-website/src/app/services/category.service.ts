import {Injectable} from '@angular/core';
import {RestApiService} from "@services/rest-api.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Category} from "@/entites/category";
import {HttpParams} from "@angular/common/http";

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

  getAll(page: number, size: number, isActive: Boolean): Observable<Category[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('isActive', isActive.toString());
    return this.restApiService.getWithParams(this.url, params);
  }

}
