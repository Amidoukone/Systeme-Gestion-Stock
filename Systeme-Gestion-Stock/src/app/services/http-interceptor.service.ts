import { Injectable } from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor{

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMsg = '';
        if (error.error instanceof ErrorEvent) {
          // Client-side error
          errorMsg = `Error: ${error.error.message}`;
        } else {
          // Server-side error
          errorMsg = `Error Code: ${error.status}, Message: ${error.message}`;
        }
        console.log(errorMsg);
        return throwError(errorMsg);
      })
    );
  }


}
