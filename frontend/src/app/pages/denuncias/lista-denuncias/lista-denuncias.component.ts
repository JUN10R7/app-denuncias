import { Component, QueryList, ViewChildren, ElementRef } from '@angular/core';

import { DenunciaService } from '../../../servicios/denuncia.service';
import { Denuncia } from '../../../model/denuncia';
import { CommonModule } from '@angular/common';
import { EnumService } from '../../../servicios/enum.service';
import { ActivatedRoute, Router } from '@angular/router';
import gsap from 'gsap';

@Component({
  selector: 'app-lista-denuncias.component',
  imports: [CommonModule],
  templateUrl: './lista-denuncias.component.html',
  styleUrl: './lista-denuncias.component.scss',
})
export class ListaDenunciasComponent {
  denuncias: Denuncia[] = [];
  categoriasMap = new Map<string, string>();
  estadosMap = new Map<string, string>();
  tipo: string = '';
  @ViewChildren('cardRef') cards!: QueryList<ElementRef>;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: DenunciaService,
    private enumService: EnumService
  ) {}

  ngOnInit(): void {
    this.loadEnums();
    this.route.params.subscribe((params) => {
      this.tipo = params['tipo'] || '';
      this.setDenuncias();
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

  verDetalle(id: string): void {
    this.router.navigate(['/app/denuncias/detalle', id]);
  }

  solicitar(id: string): void {
    this.router.navigate(['/app/solicitud', id]);
  }

  verPerfil(nombreUsuario: string) {
    this.router.navigate(['/app/perfil', nombreUsuario]);
  }

  setDenuncias() {
    switch (this.tipo) {
      case '':
        this.service.getDenuncias().subscribe((d) => (this.denuncias = d));
        break;
      case 'no-asignadas':
        this.service
          .getDenunciasMod(true)
          .subscribe((d) => (this.denuncias = d));
        break;
      case 'pendientes':
        this.service
          .getDenunciasMod(false)
          .subscribe((d) => (this.denuncias = d));
        break;
      case 'todas':
        this.service.getAllDenuncias().subscribe((d) => (this.denuncias = d));
        break;
      default:
        this.denuncias = [];
    }
  }

  loadEnums(): void {
    this.enumService.getCategorias().subscribe((categorias) => {
      categorias.forEach((cat) => this.categoriasMap.set(cat.id, cat.nombre));
    });

    this.enumService.getEstados().subscribe((estados) => {
      estados.forEach((est) => this.estadosMap.set(est.id, est.nombre));
    });
  }
}
