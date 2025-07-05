import { inject } from '@angular/core';
import { HttpInterceptorFn, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthService } from '../auth/AuthService';

export const JwtInterceptor: HttpInterceptorFn = (req, next) => {

  if (req.url.includes('/auth/login')) {
    return next(req);
  }

  const auth = inject(AuthService);
  const token = auth.getToken();

  if (token) {
    const cloned = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(cloned);
  }

  return next(req);
};
