import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FirebaseApiService {
  // Tuỳ chỉnh Http Headers
  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
  };

  constructor(private httpClient: HttpClient, private toastr: ToastrService) { }

  // Lấy tất cả dữ liệu
  getAll(link: string) {
    return this.httpClient.get<any>(link + '.json', this.httpOptions);
  }

  // Lấy dữ liệu theo id
  getOne(link: string, key: string) {
    return this.httpClient.get(link + '/' + key + '.json', this.httpOptions);
  }

  // Tạo mới dữ liệu
  post(link: string, body: any) {
    return this.httpClient.post(link + '.json', body);
  }
  // Cập nhật mới dữ liệu
  put(link: string, key: string, body: any) {
    return this.httpClient.put(link + '/' + key + '.json', body, this.httpOptions);
  }

  // Xoá dữ liệu
  delete(link: string, key: string) {
    return this.httpClient.delete(link + '/' + key + '.json');
  }

  // Xử lý lỗi
  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Nhận lỗi phía máy khách
      errorMessage = error.error.message;
    } else {
      // Nhận lỗi phía máy chủ
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      this.toastr.error(errorMessage);
      return errorMessage;
    });
  }
}
