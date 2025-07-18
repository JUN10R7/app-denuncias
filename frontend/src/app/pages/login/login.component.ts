import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',   
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  error: string = '';

  constructor(private auth: AuthService, private router: Router) { }
    
  login() {
    this.auth.login({ username: this.username, password: this.password }).subscribe({
      next: res => {
        this.auth.saveToken(res.token);
        this.router.navigate(['/app']);
      },
      error: () => this.error = 'Usuario o contraseña incorrectos'
    });
  }

  goToRegister() {
    this.router.navigate(['/registro']);
  }

}