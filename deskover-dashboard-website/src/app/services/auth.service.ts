import {HttpClient, HttpHeaders} from '@angular/common/http';
import {IUser} from '@/entites/IUser';
import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router} from "@angular/router";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: any = null;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    }),
  };

  constructor(private httpClient: HttpClient, private router: Router, private toastr: ToastrService) {
  }

  // Đăng nhập bằng email và mật khẩu, lưu token vào localStorage
  async login(email: string, password: string) {
    await this.httpClient.post(`${environment.apiURL}/auth/login`, {email, password}).pipe(
      map((data: any) => {
        localStorage.setItem('token', data.token);
        this.getProfile();
        this.router.navigate(['/']);
      })
    ).subscribe();
  }

  // Lấy thông tin người dùng
  async getProfile() {
    const data = await this.httpClient.get<IUser>(`${environment.apiURL}/auth/profile`).toPromise();
    if (data) {
      return data;
    } else {

    }
  }

  // Đăng xuất
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('gatekeeper_token');
    this.user = null;
    this.router.navigate(['/login']);
  }
}
