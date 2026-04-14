import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenStorage = inject(TokenStorageService);
  const authService = inject(AuthService);
  const token = tokenStorage.getToken();

  if (!token) {
    return next(req);
  }

  const isApiRequest = req.url.startsWith(environment.apiUrl);
  const isLoginRequest = req.url.includes('/api/auth/login');
  const isLogoutRequest = req.url.includes('/api/auth/logout');

  const requestToHandle =
    !isApiRequest || isLoginRequest
      ? req
      : req.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        });

  return next(requestToHandle).pipe(
    catchError((error: unknown) => {
      if (
        error instanceof HttpErrorResponse &&
        error.status === 401 &&
        isApiRequest &&
        !isLoginRequest &&
        !isLogoutRequest
      ) {
        authService.logout().subscribe();
      }

      return throwError(() => error);
    })
  );
};
