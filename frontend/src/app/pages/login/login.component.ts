import { Component, ElementRef, ViewChild } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import gsap from 'gsap';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  error: string = '';

  @ViewChild('usernameInput') usernameRef!: ElementRef<HTMLInputElement>;
  @ViewChild('passwordInput') passwordRef!: ElementRef<HTMLInputElement>;

  @ViewChild('container', { static: true }) containerRef!: ElementRef;

  constructor(private auth: AuthService, private router: Router) {}

  ngAfterViewInit() {
    gsap.from(this.containerRef.nativeElement, {
      opacity: 0,
      scale: 0.9,
      duration: 0.8,
      ease: 'power2.out',
    });
  }

  login() {
    const user = this.username.trim();
    const pass = this.password.trim();

    if (!user) {
      this.usernameRef.nativeElement.focus();
      return;
    }

    if (!pass) {
      this.passwordRef.nativeElement.focus();
      return;
    }

    this.error = '';
    if (!this.username.trim() || !this.password.trim()) {
      this.error = 'Por favor, completa todos los campos';
      return;
    }
    this.auth
      .login({ username: this.username, password: this.password })
      .subscribe({
        next: (res) => {
          this.auth.saveToken(res.token);
          this.router.navigate(['/app']);
        },
        error: () => {
          this.error = 'Usuario o contraseña incorrectos';
        },
      });
  }

  goToRegister() {
    this.router.navigate(['/registro']);
  }
}
