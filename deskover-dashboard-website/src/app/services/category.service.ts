import { Injectable } from '@angular/core';
import {RestApiService} from "@services/rest-api.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private restApiService: RestApiService,
    private router: Router,
    private toastr: ToastrService
  ) { }



}
