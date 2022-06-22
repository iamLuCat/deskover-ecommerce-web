import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from "rxjs/operators";
import {AlertUtils} from "@/utils/alert-utils";

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  constructor(private httpClient: HttpClient) {
  }

  get(link: string): Observable<any> {
    return this.httpClient.get(link).pipe(catchError(RestApiService.handleError));
  }

  getWithParams(link: string, params: HttpParams): Observable<any> {
    return this.httpClient.get(link, {params}).pipe(catchError(RestApiService.handleError));
  }

  getOne(link: string, id: any): Observable<any> {
    return this.httpClient.get(link + '/' + id).pipe(catchError(RestApiService.handleError));
  }

  post(link: string, body: any): Observable<any> {
    return this.httpClient.post(link, body).pipe(catchError(RestApiService.handleError));
  }

  put(link: string, body: any): Observable<any> {
    return this.httpClient.put(link, body).pipe(catchError(RestApiService.handleError));
  }

  delete(link: string, id: number) {
    return this.httpClient.delete(link + '/' + id).pipe(catchError(RestApiService.handleError));
  }

  private static handleError(error: any) {
    let errorMessage = 'Unknown error!';
    if (error.error) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status} - Message: ${error.message}`;
    }
    AlertUtils.toastError(errorMessage);
    return throwError(errorMessage);
  }
}
