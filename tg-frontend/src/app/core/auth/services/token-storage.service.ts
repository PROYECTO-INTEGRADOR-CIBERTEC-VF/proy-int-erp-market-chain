import { Injectable } from '@angular/core';

import { LoginResponse } from '../models/auth.models';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private readonly tokenKey = 'token';
  private readonly userKey = 'sessionUser';

  saveSession(session: LoginResponse): void {
    localStorage.setItem(this.tokenKey, session.token);
    localStorage.setItem(this.userKey, JSON.stringify(session));
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  clearSession(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }
}
