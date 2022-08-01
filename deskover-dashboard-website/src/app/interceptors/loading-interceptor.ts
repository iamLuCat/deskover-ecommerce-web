import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {Loading} from "notiflix";

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    Loading.standard({
      backgroundColor: 'rgba(255,255,255,0.8)',
    });
    return next.handle(request).pipe(
      tap(() => Loading.remove())
    );
  }
}
