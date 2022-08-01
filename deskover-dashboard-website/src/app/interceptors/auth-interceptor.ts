import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {StorageConstants} from "@/constants/storage-constants";

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem(StorageConstants.TOKEN);
    let authReq = req;

    if (token) {
      authReq = req.clone({
        headers: req.headers
          .set('Authorization', `Bearer ${token}`)
      });
    }

    return next.handle(authReq);
  }
}
