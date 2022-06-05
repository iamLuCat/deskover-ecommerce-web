import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ToastrService} from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  authToken = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE2NTQ0MTg5MzksImV4cCI6MTY1NDUwNTMzOX0.EOMOTX11zJ9vksPmntTjGB5ZD8XgN82iR5orW5nAm0IALBNVPny7k3wJkv1QPXi2St66vXg6ZSLX4jGViwoq2A';

  // Tuỳ chỉnh Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authToken}`
    })
  };

  constructor(private httpClient: HttpClient, private toastr: ToastrService) {
  }

  // Lấy tất cả dữ liệu
  getAll(link: string): Observable<any> {
    return this.httpClient.get<any>(link, this.httpOptions);
  }

  // Lấy dữ liệu theo id
  getOne(link: string, id: any): Observable<any> {
    return this.httpClient.get(link + '/' + id, this.httpOptions);
  }

  // Tạo mới dữ liệu
  post(link: string, body: any): Observable<any> {
    return this.httpClient.post(link, body, this.httpOptions);
  }

  // Cập nhật mới dữ liệu
  put(link: string, id: number, body: any): Observable<any> {
    return this.httpClient.put(link + '/' + id, body, this.httpOptions);
  }

  // Xoá dữ liệu
  delete(link: string, id: number) {
    return this.httpClient.delete(link + '/' + id, this.httpOptions);
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
