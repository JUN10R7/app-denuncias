import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { mapToCanActivateChild, Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly TOKEN_KEY = 'jwt-token';
  private readonly ROLE_KEY = 'jwt-role';
  private readonly USERNAME_KEY = 'jwt-username';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { username: string; password: string }) {
    return this.http.post<{ token: string }>(
      `${environment.apiUrl}/auth/login`,
      credentials
    );
  }

  saveToken(token: string) {
    const payload = JSON.parse(atob(token.split('.')[1]));
    localStorage.setItem(this.TOKEN_KEY, token);
    localStorage.setItem(this.ROLE_KEY, payload.role);
    localStorage.setItem(this.USERNAME_KEY, payload.sub);
  }

  getToken() {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getRole() {
    return localStorage.getItem(this.ROLE_KEY);
  }

  getUsername() {
    return localStorage.getItem(this.USERNAME_KEY);
  }

  logout() {
    const preservedTheme = localStorage.getItem('theme');
    localStorage.clear();
    if (preservedTheme) {
      localStorage.setItem('theme', preservedTheme);
    }
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!this.getToken() && !this.isTokenExpired();
  }

  isUser(): boolean {
    return this.getRole() === 'USER';
  }

  isMod(): boolean {
    return this.getRole() === 'MOD';
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    const payload = JSON.parse(atob(token.split('.')[1]));
    const expiry = payload.exp;
    return Math.floor(Date.now() / 1000) > expiry;
  }
}
