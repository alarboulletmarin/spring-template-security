// === Import : NPM
import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpEvent,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

// === Import : LOCAL
import { APP_CONSTANTS } from 'src/app/app.constant';
import { AuthService } from '../auth/auth.service';

// If no token, do next without setting headers
// Else, TokenInterceptor intercepts all requests and set the new header with Bearer token.
// Furthermore, httpResponse header contains the new token to store.
@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const authToken = this.authService.currentTokenValue;
    let header = {};
    if (authToken) {
      header = {
        setHeaders: { Authorization: `Bearer ${authToken}` },
      };
    }

    const clone = req.clone(header);
    return next.handle(clone).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          const token = event.headers.get(APP_CONSTANTS.headers.token);
          if (token) {
            this.authService.setToken(token);
          }
        }
      })
    );
  }
}
