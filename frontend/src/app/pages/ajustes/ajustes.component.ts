import { Component } from '@angular/core';
import { UsuarioService } from '../../servicios/usuario.service';
import { Usuario } from '../../model/usuario';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { gsap } from 'gsap';

@Component({
  selector: 'app-ajustes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ajustes.component.html',
  styleUrls: ['./ajustes.component.scss'],
})
export class AjustesComponent {
  usuario: Usuario = {
    id: 0,
    nombres: '',
    apellidos: '',
    username: '',
    password: '',
    dni: '',
    email: '',
    rol: '',
    enabled: true,
  };

  editandoEmail: boolean = false;
  editandoPassword: boolean = false;

  constructor(private service: UsuarioService) {}

  ngOnInit() {
    this.service.getUsuario().subscribe({
      next: (usuario) => {
        this.usuario = usuario;
      },
      error: (error) => {
        console.error('Error al obtener el usuario:', error);
      },
    });
  }

  ngAfterViewInit() {
    gsap.from('input:not([disabled])', {
      opacity: 0,
      y: 10,
      duration: 0.4,
      ease: 'power2.out',
      stagger: 0.05,
    });
  }

  actualizarUsuario(form: NgForm) {
    if (!form.valid) return;
    const { username, password, email, rol } = this.usuario;
    const data = { username, password, email, rol };
    this.service.updateUsuario(data).subscribe({
      next: (usuarioActualizado) => {
        this.usuario = { ...this.usuario, ...usuarioActualizado };
        gsap.to('form', {
          backgroundColor: '#d1fae5',
          duration: 0.5,
          yoyo: true,
          repeat: 1,
        });
        console.log('Usuario actualizado:', this.usuario);
      },
      error: (error) => {
        console.error('Error al actualizar el usuario:', error);
      },
    });
  }
}
