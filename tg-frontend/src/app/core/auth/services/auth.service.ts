import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { catchError, finalize, of } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/auth.models';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly tokenStorage = inject(TokenStorageService);
  private readonly router = inject(Router);

  private readonly authUrl = `${environment.apiUrl}/api/auth`;
  private isLoggingOut = false;

  login(payload: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.authUrl}/login`, payload)
      .pipe(tap((session) => this.tokenStorage.saveSession(session)));
  }

  logout(): Observable<void> {
    if (this.isLoggingOut) {
      return of(void 0);
    }

    this.isLoggingOut = true;

    const token = this.tokenStorage.getToken();
    const headers = token
      ? new HttpHeaders({ Authorization: `Bearer ${token}` })
      : undefined;

    return this.http.post<void>(`${this.authUrl}/logout`, {}, { headers }).pipe(
      catchError(() => of(void 0)),
      finalize(() => {
        this.clearSession();
        this.isLoggingOut = false;
        void this.router.navigateByUrl('/login');
      })
    );
  }

  clearSession(): void {
    this.tokenStorage.clearSession();
  }
}
