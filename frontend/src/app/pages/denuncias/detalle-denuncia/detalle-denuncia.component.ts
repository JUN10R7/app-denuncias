import { Component } from '@angular/core';
import { DenunciaService } from '../../../servicios/denuncia.service';
import { Denuncia } from '../../../model/denuncia';
import { ActivatedRoute, Router } from '@angular/router';
import { EnumService } from '../../../servicios/enum.service';
import { ListaSolicitudesComponent } from '../../solicitudes/lista-solicitudes/lista-solicitudes.component';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-detalle-denuncia.component',
  standalone: true,
  imports: [CommonModule, ListaSolicitudesComponent],
  templateUrl: './detalle-denuncia.component.html',
  styleUrl: './detalle-denuncia.component.scss',
})
export class DetalleDenunciaComponent {
  denuncia: Denuncia = {} as Denuncia;
  id: string = '';
  categoriasMap = new Map<string, string>();
  estadosMap = new Map<string, string>();
  mostrarContenido = false;

  constructor(
    private service: DenunciaService,
    private route: ActivatedRoute,
    private enumService: EnumService,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.id = params['id'] || '';
      this.loadEnums();
      this.setDenuncia();
    });
  }

  setDenuncia() {
    this.service.getDenunciaById(Number(this.id)).subscribe((denuncia) => {
      this.denuncia = denuncia;
      this.mostrarContenido = true;
    });
  }

  loadEnums(): void {
    this.enumService.getCategorias().subscribe((categorias) => {
      categorias.forEach((cat) => this.categoriasMap.set(cat.id, cat.nombre));
    });

    this.enumService.getEstados().subscribe((estados) => {
      estados.forEach((est) => this.estadosMap.set(est.id, est.nombre));
    });
  }

  verPerfil(nombreUsuario: string): void {
    this.router.navigate(['/app/perfil', nombreUsuario]);
  }

  crearSolicitud(): void {
    this.router.navigate(['/app/solicitudes/crear'], {
      queryParams: { idDenuncia: this.denuncia.id },
    });
  }

  isAdmin(): boolean {
    return this.auth.isAdmin();
  }

  getUsername() {
    return this.auth.getUsername();
  }

  editarDenuncia(id: number | undefined) {
    if (id === undefined) return;
    this.router.navigate(['/app/denuncias/editar', id])
  }
}
