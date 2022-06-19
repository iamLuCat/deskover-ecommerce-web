import {Injectable} from '@angular/core';
import {RestApiService} from '@services/rest-api.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {environment} from "../../environments/environment";
import {Admin} from "@/entites/admin";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  constructor(
    private restApiService: RestApiService,
    private router: Router,
    private toastr: ToastrService
  ) {}

}
