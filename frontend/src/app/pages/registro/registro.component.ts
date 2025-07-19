import { Component } from '@angular/core';
import { UsuarioService } from '../../servicios/usuario.service';
import { AuthService } from '../../auth/auth.service';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Enum } from '../../model/enum';
import { EnumService } from '../../servicios/enum.service';
import { Usuario } from '../../model/usuario';
import { Router } from '@angular/router';
import { gsap } from 'gsap';
import { CommonModule } from '@angular/common';
import { CustomSelectEnumComponent } from '../../layout/custom-select-enum/custom-select-enum.component';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, CustomSelectEnumComponent, ReactiveFormsModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss'],
})
export class RegistroComponent {
  usuario: Usuario = {} as Usuario;
  roles: Enum[] = [];
  error: String = '';

  usuarioForm = new FormGroup({
    nombres: new FormControl('', [Validators.required]),
    apellidos: new FormControl('', [Validators.required]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    repeatPassword: new FormControl('', [Validators.required]),
    dni: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required]),
    rol: new FormControl('USER'),
  });

  constructor(
    private service: UsuarioService,
    private auth: AuthService,
    private enumService: EnumService,
    private router: Router
  ) {}

  ngOnInit() {
    if (this.isAdmin()) this.loadRoles();
    gsap.from('#registro-form-container', {
      duration: 0.8,
      opacity: 0,
      y: 30,
      ease: 'power3.out',
    });
  }

  registrarUsuario() {
    this.error = '';
    if (this.usuarioForm.invalid) return;
    const { password, repeatPassword } = this.usuarioForm.value;
    if (password !== repeatPassword) {
      this.error = 'Las contraseñas no coinciden';
      gsap.from('#registro-form-container', {
        duration: 0.8,
        opacity: 0,
        y: 30,
        ease: 'power3.out',
      });
      return;
    }

    this.usuario = { ...this.usuarioForm.value } as Usuario;
    this.service.registerUsuario(this.usuario).subscribe({
      next: (text) => {
        gsap.to('form', {
          opacity: 0,
          duration: 0.5,
          ease: 'power2.inOut',
          onComplete: () => {
            const targetRoute = this.isAdmin()
              ? ['/app/usuarios']
              : ['app/login'];
            setTimeout(() => this.router.navigate(targetRoute), 100);
          },
        });
      },
      error: (err) => {
        console.log(err);
        this.error =
          err.error?.message || 'Hubo un error al registrar el usuario.';
        gsap.fromTo(
          '#registro-form-container',
          { scale: 0.98 },
          { scale: 1, duration: 0.3, ease: 'bounce.out' }
        );
      },
    });
  }

  isAdmin(): boolean {
    return this.auth.isAdmin();
  }

  loadRoles(): void {
    this.enumService.getRoles().subscribe((roles) => (this.roles = roles));
  }
}
