import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {RestApiService} from '@services/rest-api.service';
import {Admin} from "@/entites/admin";
import {AlertUtils} from "@/utils/alert-utils";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: Admin = null;

  constructor(
    private restApiService: RestApiService,
    private router: Router
  ) {
  }

  // Đăng nhập bằng email và mật khẩu, lưu token vào localStorage
  async login({username, password}) {
    try {
      const data = await this.restApiService
        .post(`${environment.globalUrl.login}`, {
          username,
          password
        })
        .toPromise();
      localStorage.setItem('token', data.token);
      await this.getProfile();
      await this.router.navigate(['/']);
    } catch (e) {
      AlertUtils.toastError(e);
    }
  }

  // Lấy thông tin người dùng
  async getProfile() {
    try {
      this.user = await this.restApiService
        .get(`${environment.globalUrl.profile}`)
        .toPromise();
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
