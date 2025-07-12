import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { NavOptionsComponent } from '../../layout/nav-options/nav-options.component';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-solicitudes.component',
  standalone: true,
  imports: [NavOptionsComponent, RouterOutlet, CommonModule],
  templateUrl: './solicitudes.component.html',
  styleUrl: './solicitudes.component.scss'
})
export class SolicitudesComponent {

  rutas: { nombre: string, ruta: string }[] = [];

  constructor(private auth: AuthService) {}
  
  ngOnInit(): void {
    this.rutas = [
      { nombre: 'Mis solicitudes', ruta: '' },
    ];
    if (this.auth.isAdmin()) {
      this.rutas.push({ nombre: 'Solicitudes', ruta: 'todas' });
    }
    this.rutas.push({ nombre: 'Nueva solicitud', ruta: 'crear' });
  }
}
