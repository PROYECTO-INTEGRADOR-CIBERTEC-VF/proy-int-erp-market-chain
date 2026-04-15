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

  const isApiRequest = req.url.startsWith(environment.apiUrl);
  const isLoginRequest = req.url.includes('/api/auth/login');
  const isLogoutRequest = req.url.includes('/api/auth/logout');
  const hasAuthorizationHeader = req.headers.has('Authorization');
  const sessionAuthorization = token ? `Bearer ${token}` : '';

  const shouldAttachSessionToken =
    Boolean(token) &&
    isApiRequest &&
    !isLoginRequest &&
    !isLogoutRequest &&
    !hasAuthorizationHeader;

  const requestToHandle =
    !shouldAttachSessionToken
      ? req
      : req.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        });

  return next(requestToHandle).pipe(
    catchError((error: unknown) => {
      const requestAuthorization = requestToHandle.headers.get('Authorization') ?? '';
      const usesSessionToken =
        sessionAuthorization.length > 0 && requestAuthorization === sessionAuthorization;

      if (
        error instanceof HttpErrorResponse &&
        error.status === 401 &&
        isApiRequest &&
        !isLoginRequest &&
        !isLogoutRequest &&
        (!requestAuthorization || usesSessionToken)
      ) {
        authService.logout().subscribe();
      }

      return throwError(() => error);
    })
  );
};
