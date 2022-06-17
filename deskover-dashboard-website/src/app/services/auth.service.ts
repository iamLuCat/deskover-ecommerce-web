import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {RestApiService} from '@services/rest-api.service';
import {AdminService} from "@services/admin.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: any = null;

  constructor(
    private restApiService: RestApiService,
    private adminService: AdminService,
    private router: Router,
    private toastr: ToastrService
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
      this.router.navigate(['/']);
    } catch (e) {
      console.error(e);
      this.toastr.error(e);
    }
  }

  // Đăng ký
  async register({email, password}) {
    try {
      const data = await this.restApiService
        .post(
          `${environment.globalUrl.baseApi}`,
          {
            email,
            password
          }
        )
        .toPromise();
      localStorage.setItem('token', data.accessToken);
      await this.getProfile();
      this.router.navigate(['/']);
    } catch (e) {
      this.toastr.error(e);
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
