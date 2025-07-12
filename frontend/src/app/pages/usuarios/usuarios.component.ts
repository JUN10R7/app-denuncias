import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavOptionsComponent } from '../../layout/nav-options/nav-options.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-usuarios.component',
  imports: [CommonModule, NavOptionsComponent, RouterOutlet],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent {

    rutas: { nombre: string, ruta: string }[] = [
        { nombre: 'Usuarios', ruta: '' },
        { nombre: 'Moderadores', ruta: 'mods' },
        { nombre: 'Administradores', ruta: 'admins' }
    ];

}
