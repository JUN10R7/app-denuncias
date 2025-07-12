import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavOptionsComponent } from '../../layout/nav-options/nav-options.component';
import { RouterOutlet } from '@angular/router';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-denuncias.component',
  imports: [CommonModule, NavOptionsComponent, RouterOutlet],
  templateUrl: './denuncias.component.html',
  styleUrl: './denuncias.component.scss',
})
export class DenunciasComponent {

  rutas: { nombre: string, ruta: string }[] = [];

  constructor(private auth: AuthService) {}
  
  ngOnInit(): void {
    this.rutas = [
      { nombre: 'Mis denuncias', ruta: '' },
    ];
    if (this.auth.isMod() || this.auth.isAdmin()) {
      this.rutas.push({ nombre: 'Sin asignar', ruta: 'no-asignadas' });
    }
    if (this.auth.isMod()) {
      this.rutas.push({ nombre: 'Pendientes', ruta: 'pendientes' });
    }
    if (this.auth.isAdmin()) {
      this.rutas.push({ nombre: 'Todas', ruta: 'todas' });
    }
    this.rutas.push({ nombre: 'Nueva denuncia', ruta: 'crear' });
  }
}
