import {
  Component,
  ElementRef,
  QueryList,
  ViewChildren,
  Input,
} from '@angular/core';
import { Solicitud } from '../../../model/solicitud';
import { SolicitudService } from '../../../servicios/solicitud.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EnumService } from '../../../servicios/enum.service';
import { CommonModule } from '@angular/common';
import gsap from 'gsap';

@Component({
  selector: 'app-lista-solicitudes',
  imports: [CommonModule],
  templateUrl: './lista-solicitudes.component.html',
  styleUrl: './lista-solicitudes.component.scss',
})
export class ListaSolicitudesComponent {
  solicitudes: Solicitud[] = [];
  estadosMap = new Map<string, string>();
  tipoSolicitudMap = new Map<string, string>();
  tipo: string = '';
  @ViewChildren('cardRef') cards!: QueryList<ElementRef>;
  @Input() idDenuncia: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: SolicitudService,
    private enumService: EnumService
  ) {}

  ngOnInit() {
    this.loadEnums();
    this.route.params.subscribe((params) => {
      this.tipo = params['tipo'] || '';
      this.setSolicitudes();
    });
  }

  ngAfterViewInit(): void {
    if (this.cards.length > 0) {
      this.animarCards(this.cards);
    }

    this.cards.changes.subscribe((cards: QueryList<ElementRef>) => {
      this.animarCards(cards);
    });
  }

  animarCards(cards: QueryList<ElementRef>): void {
    gsap.from(
      cards.map((c) => c.nativeElement),
      {
        opacity: 0,
        y: 50,
        scale: 0.95,
        duration: 0.6,
        stagger: 0.1,
        ease: 'power2.out',
      }
    );
  }

  verPerfil(nombreUsuario: string): void {
    this.router.navigate(['/app/perfil', nombreUsuario]);
  }

  verDetalle(id: string): void {
    this.router.navigate(['detalle', id], { relativeTo: this.route });
  }

  private setSolicitudes() {
    if (this.idDenuncia != 0) {
      this.service.getSolicitudesByDenuncia(this.idDenuncia).subscribe((s) => this.solicitudes = s)
      return;
    }
    switch (this.tipo) {
      case '':
        this.service.getSolicitudes().subscribe((s) => (this.solicitudes = s));
        break;
      case 'todas':
        this.service
          .getSolicitudesAdmin()
          .subscribe((s) => (this.solicitudes = s));
        break;
      default:
        this.solicitudes = [];
    }
  }

  loadEnums(): void {
    this.enumService.getTiposSolicitud().subscribe((tiposSolicitud) => {
      tiposSolicitud.forEach((tipo) =>
        this.tipoSolicitudMap.set(tipo.id, tipo.nombre)
      );
    });

    this.enumService.getEstados().subscribe((estados) => {
      estados.forEach((est) => this.estadosMap.set(est.id, est.nombre));
    });
  }
}
