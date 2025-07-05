import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from './AuthService';

export const authGuard:CanActivateFn=()=>{
  const auth=inject(AuthService);
  const router = inject(Router);
  return auth.isAuthenticated() || (router.navigate(['/login']), false);
};