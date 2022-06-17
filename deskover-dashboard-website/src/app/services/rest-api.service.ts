import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ToastrService} from 'ngx-toastr';
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  constructor(private httpClient: HttpClient, private toastr: ToastrService) {
  }

  get(link: string): Observable<any> {
    return this.httpClient.get(link).pipe(catchError(this.handleError));
  }

  getOne(link: string, id: any): Observable<any> {
    return this.httpClient.get(link + '/' + id).pipe(catchError(this.handleError));
  }

  post(link: string, body: any): Observable<any> {
    return this.httpClient.post(link, body).pipe(catchError(this.handleError));
  }

  put(link: string, id: number, body: any): Observable<any> {
    return this.httpClient.put(link + '/' + id, body).pipe(catchError(this.handleError));
  }

  delete(link: string, id: number) {
    return this.httpClient.delete(link + '/' + id).pipe(catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage = 'Unknown error!';
    if (error.error.message) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status} - Message: ${error.message}`;
    }
    return throwError(errorMessage);
  }
}
