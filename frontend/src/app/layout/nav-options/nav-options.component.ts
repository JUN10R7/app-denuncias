import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav-options',
  imports: [RouterModule, CommonModule],
  templateUrl: './nav-options.component.html',
  styleUrl: './nav-options.component.scss'
})
export class NavOptionsComponent {

  @Input() rutas: { nombre: string, ruta: string }[] = [];
  basePath: string = '';

  constructor(private router: Router) {
    const url = this.router.url;
    const match = url.match(/^\/app\/[^\/]+/);
    this.basePath = match ? match[0] : '/app';
  }

}
