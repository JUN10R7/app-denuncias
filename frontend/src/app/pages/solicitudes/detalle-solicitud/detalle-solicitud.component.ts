import { Component } from '@angular/core';
import { SolicitudService } from '../../../servicios/solicitud.service';
import { Solicitud } from '../../../model/solicitud';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { EnumService } from '../../../servicios/enum.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detalle-solicitud.component',
  imports: [CommonModule, FormsModule],
  templateUrl: './detalle-solicitud.component.html',
  styleUrl: './detalle-solicitud.component.scss'
})
export class DetalleSolicitudComponent {

  id: String = '';
  solicitud: Solicitud = {} as Solicitud;
  estadosMap = new Map<string, string>();
  tipoSolicitudMap = new Map<string, string>();

  constructor(
    private service: SolicitudService,
    private route: ActivatedRoute,
    private enumService: EnumService,
    private router: Router
  ) { };

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.id = params['id'] || '';
      this.loadEnums();
      this.setSolicitud();
    });
  }

  setSolicitud() {
    this.service.getSolicitud(Number(this.id)).subscribe((solicitud) => {
      this.solicitud  = solicitud;
    })
  }

  loadEnums(): void {
    this.enumService.getTiposSolicitud().subscribe((tipoSolicitud) => {
      tipoSolicitud.forEach((tipo) => this.tipoSolicitudMap.set(tipo.id, tipo.nombre));
    });

    this.enumService.getEstados().subscribe((estados) => {
      estados.forEach((est) => this.estadosMap.set(est.id, est.nombre));
    });
  }

  verPerfil(username: string): void {
    this.router.navigate(['/perfil', username]);
  }

  isAdmin(): boolean {
    return localStorage.getItem('rol') === 'ADMIN';
  }

  editarSolicitud(id: number): void {
    this.router.navigate(['/solicitudes/editar', id]);
  }

  aceptarSolicitud() {
    
  }
  rechazarSolicitud() {

  }
}
