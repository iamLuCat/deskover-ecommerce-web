import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ToastrService} from 'ngx-toastr';
import {catchError, retry} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    })
  };

  constructor(private httpClient: HttpClient, private toastr: ToastrService) {
  }

  get(link: string): Observable<any> {
    return this.httpClient.get(link, this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  getOne(link: string, id: any): Observable<any> {
    return this.httpClient.get(link + '/' + id, this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  post(link: string, body: any): Observable<any> {
    return this.httpClient.post(link, body, this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  put(link: string, id: number, body: any): Observable<any> {
    return this.httpClient.put(link + '/' + id, body, this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  delete(link: string, id: number) {
    return this.httpClient.delete(link + '/' + id, this.httpOptions).pipe(retry(1), catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Lỗi phía máy khách
      errorMessage = error.error.message;
    } else {
      // Lỗi phía máy chủ
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      // this.toastr.error(errorMessage);
      return errorMessage;
    });
  }
}
