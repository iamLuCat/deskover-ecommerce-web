import {Injectable} from '@angular/core';
import {RestApiService} from "@services/rest-api.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Category} from "@/entites/category";

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

  getAll(page: number, size: number): Observable<Category[]> {
    return this.restApiService.getAll( `${this.url}/actived?page=${page}&size=${size}`);
  }

}
