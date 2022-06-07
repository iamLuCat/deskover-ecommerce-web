import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ToastrService} from 'ngx-toastr';
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RestApiService {
  // Tuỳ chỉnh Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    })
  };

  constructor(private httpClient: HttpClient, private toastr: ToastrService) {
  }

  get(link: string): Observable<any> {
    return this.httpClient.get(link, this.httpOptions).pipe(catchError(this.handleError));
  }

  getOne(link: string, id: any): Observable<any> {
    return this.httpClient.get(link + '/' + id, this.httpOptions).pipe(catchError(this.handleError));
  }

  post(link: string, body: any): Observable<any> {
    return this.httpClient.post(link, body, this.httpOptions).pipe(catchError(this.handleError));
  }

  put(link: string, id: number, body: any): Observable<any> {
    return this.httpClient.put(link + '/' + id, body, this.httpOptions).pipe(catchError(this.handleError));
  }

  delete(link: string, id: number) {
    return this.httpClient.delete(link + '/' + id, this.httpOptions).pipe(catchError(this.handleError));
  }

  handleError(error: any) {
    /*let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Nhận lỗi phía máy khách
      errorMessage = error.error.message;
    } else {
      // Nhận lỗi phía máy chủ
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);*/
    return throwError(error.error.message);
  }
}
