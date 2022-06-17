import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ToastrService} from 'ngx-toastr';
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  constructor(private httpClient: HttpClient, private toastr: ToastrService) {
  }

  getAll(link: string): Observable<any> {
    return this.httpClient.get(link).pipe(catchError(RestApiService.handleError));
  }

  getAllWithPagination(link: string, page: number, size: number): Observable<any> {
    const params = new HttpParams();
    params.append('page', page.toString());
    params.append('size', size.toString());
    return this.httpClient.get(link, {params}).pipe(catchError(RestApiService.handleError));
  }

  getOne(link: string, id: any): Observable<any> {
    return this.httpClient.get(link + '/' + id).pipe(catchError(RestApiService.handleError));
  }

  create(link: string, body: any): Observable<any> {
    return this.httpClient.post(link, body).pipe(catchError(RestApiService.handleError));
  }

  update(link: string, id: number, body: any): Observable<any> {
    return this.httpClient.put(link + '/' + id, body).pipe(catchError(RestApiService.handleError));
  }

  delete(link: string, id: number) {
    return this.httpClient.delete(link + '/' + id).pipe(catchError(RestApiService.handleError));
  }

  private static handleError(error: any) {
    let errorMessage = 'Unknown error!';
    if (error.error.message) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status} - Message: ${error.message}`;
    }
    return throwError(errorMessage);
  }
}
