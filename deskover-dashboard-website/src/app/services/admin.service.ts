import {Injectable} from '@angular/core';
import {RestApiService} from '@services/rest-api.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {environment} from "../../environments/environment";
import {IAdmin} from "@/entites/IAdmin";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  constructor(
    private restApiService: RestApiService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  getAdmin(id: number) {
    return this.restApiService.getOne(`${environment.globalUrl.adminApi}`, id).toPromise();
  }
}
