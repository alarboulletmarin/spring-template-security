import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environments';
import { Router } from '@angular/router';
import { APP_CONSTANTS } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly JWT_TOKEN = 'currentToken';
  // Observables
  private currentTokenSubject = new BehaviorSubject<string | null>(
    JSON.parse(localStorage.getItem(this.JWT_TOKEN) || '""')
  );
  public currentToken: Observable<string | null> =
    this.currentTokenSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  public get currentTokenValue(): string | null {
    return this.currentTokenSubject.getValue();
  }

  login(username: string, password: string): Observable<void> {
    return this.http
      .post<void>(`${environment.apiURL}/api/auth/login`, {
        username,
        password,
      })
      .pipe();
  }

  public logout(): void {
    localStorage.clear();
    this.currentTokenSubject.next(null);
    this.router.navigate([APP_CONSTANTS.routerLinks.login]);
  }

  public setToken(token: string): void {
    localStorage.setItem(this.JWT_TOKEN, JSON.stringify(token));
    this.currentTokenSubject.next(token);
  }

  getJwtToken(): string | null {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  isLoggedIn(): boolean {
    return !!this.getJwtToken();
  }
}
