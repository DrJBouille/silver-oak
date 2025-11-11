import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LocalStorageService } from '../services/local-storage/local-storage.service';
import { AuthService } from '../services/auth/auth.service';
import { TokensDTO } from '../model/TokensDTO';
import { Router } from '@angular/router';
import { catchError, switchMap, throwError } from 'rxjs';
import { RefreshTokenRequest } from '../model/RefreshTokenRequest';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.endsWith('/auth/refresh-token')) return next(req);

  const localStorageService = inject(LocalStorageService);
  const authService = inject(AuthService);
  const router = inject(Router);

  const token: TokensDTO | null = localStorageService.getItem(authService.localStorageKey);

  const cloned = token ? req.clone({ setHeaders: {Authorization: `Bearer ${token.accessToken}`} }) : req;

  return next(cloned).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status !== 401 || !token?.refreshToken) return throwError(() => error);

      return authService.refreshToken(new RefreshTokenRequest(token.refreshToken)).pipe(
        switchMap(newToken => {
          localStorageService.setItem(authService.localStorageKey, newToken);

          const retryReq = req.clone({
            setHeaders: { Authorization: `Bearer ${newToken.accessToken}` },
          });
          return next(retryReq);
        }),
        catchError(refreshError => {
          localStorageService.removeItem(authService.localStorageKey);
          router.navigate(['/login']);
          return throwError(() => refreshError);
        })
      )
    })
  );
};
