import {HttpClient, HttpHeaders} from '@angular/common/http';
import {IUser} from '@/entites/IUser';
import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router} from "@angular/router";
import {map} from "rxjs/operators";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: any = null;

  constructor(private restApiService: RestApiService, private router: Router, private toastr: ToastrService) {
  }

  // Đăng nhập bằng email và mật khẩu, lưu token vào localStorage
  async login(email: string, password: string) {
    await this.restApiService.post(`${environment.apiURL}/auth/login`, {email, password}).subscribe(
      (data: any) => {
        localStorage.setItem('token', data.token);
        this.getProfile();
        this.router.navigate(['/']);
      }
    );
  }

  // Lấy thông tin người dùng
  async getProfile() {
    const data = await this.restApiService.get(`${environment.apiURL}/auth/profile`).subscribe(
      (data: any) => {
        this.user = data;
      },
      (error: any) => {
        this.logout();
        console.error(error);
      }
    );
  }

  // Đăng xuất
  logout() {
    localStorage.removeItem('token');
    this.user = null;
    this.router.navigate(['/login']);
  }
}
