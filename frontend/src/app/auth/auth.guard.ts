import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from './auth.service';

export const AuthGuard:CanActivateFn=()=>{
  const auth=inject(AuthService);
  const router = inject(Router);
  return auth.isAuthenticated() || (router.navigate(['/login']), false);
};