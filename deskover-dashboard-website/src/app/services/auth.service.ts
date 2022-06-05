import { HttpClient } from '@angular/common/http';
import { IUser } from '@/entites/IUser';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  url = `${environment.apiURL}/auth/login`;
  user: IUser;
  token: any;

  constructor(private httpClient: HttpClient, private toastr: ToastrService) { }

  // Đăng nhập với email và password, lấy token khi đăng nhập thành công
  async login(email: string, password: string) {
    const data = await this.httpClient.post<IUser>(this.url, { email, password }).toPromise();
    if (data) {
      return data.token;
    }
  }

  // Lấy thông tin người dùng
  async getProfile() {
    const data = await this.httpClient.get<IUser>('/api/auth/profile').toPromise();
    if (data) {
      this.user = data;
      return data;
    }
  }
}
