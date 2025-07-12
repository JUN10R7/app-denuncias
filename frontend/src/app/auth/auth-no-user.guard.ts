import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const AuthNoUserGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  return auth.isAuthenticated() && (auth.isAdmin() || auth.isMod());
};
