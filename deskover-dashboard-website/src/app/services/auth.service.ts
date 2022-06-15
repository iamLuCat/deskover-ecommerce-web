import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router} from "@angular/router";
import {RestApiService} from "@services/rest-api.service";
import {HttpClient} from "@angular/common/http";
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: any = null;

  constructor(private httpClient: HttpClient, private restApiService: RestApiService, private router: Router, private toastr: ToastrService) {
  }

  // Đăng nhập bằng email và mật khẩu, lưu token vào localStorage
  async login({email, password}) {
    try {
      const data = await this.httpClient.post<any>(`${environment.apiURL}/auth/login`, {email, password}).pipe(catchError(this.handleError)).toPromise();
      localStorage.setItem('token', data.accessToken);
      await this.getProfile();
      this.router.navigate(['/']);
    } catch (e) {
      this.toastr.error(e);
    }
  }

  // Đăng ký
  async register({email, password}) {
    try {
      const data = await this.httpClient.post<any>(`${environment.apiURL}/auth/register`, {email, password}).pipe(catchError(this.handleError)).toPromise();
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
      this.user = await this.httpClient.get<any>(`${environment.apiURL}/auth/profile`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      }).pipe(catchError(this.handleError)).toPromise();
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

  handleError(error: any) {
    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\n - Message: ${error.message}`;
    }

    return throwError(errorMessage);
  }
}
