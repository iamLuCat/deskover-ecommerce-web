import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router} from "@angular/router";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: any = null;

  constructor(private restApiService: RestApiService, private router: Router, private toastr: ToastrService) {}

  // Đăng nhập bằng email và mật khẩu, lưu token vào localStorage
  async login({email, password}) {
    try {
      const data = await this.restApiService.post(`${environment.apiURL}/auth/login`, {email, password}).toPromise();
      localStorage.setItem('token', data.accessToken);
      await this.getProfile();
      this.router.navigate(['/']);
    } catch (e) {
      this.toastr.error(e);
    }
  }

  // Đăng ký
  async register({email, password}) {
    await this.restApiService.post(`${environment.apiURL}/auth/register`, {email, password}).subscribe(
      (data: any) => {
        localStorage.setItem('token', data.token);
        this.getProfile();
        this.router.navigate(['/']);
      }
    );
  }

  // Lấy thông tin người dùng
  async getProfile() {
    try {
      this.user = await this.restApiService.get(`${environment.apiURL}/auth/profile`).toPromise();
    } catch (e) {
      this.logout();
      throw e;
    }
  }

  // Đăng xuất
  logout() {
    localStorage.removeItem('token');
    this.user = null;
    this.router.navigate(['/login']);
  }
}
