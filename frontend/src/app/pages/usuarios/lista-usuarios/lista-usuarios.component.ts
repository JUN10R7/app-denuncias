import {
  Component,
  QueryList,
  ViewChildren,
  ElementRef,
  AfterViewInit,
} from '@angular/core';
import { Usuario } from '../../../model/usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { EnumService } from '../../../servicios/enum.service';
import { UsuarioService } from '../../../servicios/usuario.service';
import { CommonModule } from '@angular/common';
import gsap from 'gsap';

@Component({
  selector: 'app-lista-usuarios.component',
  imports: [CommonModule],
  templateUrl: './lista-usuarios.component.html',
  styleUrl: './lista-usuarios.component.scss',
})
export class ListaUsuariosComponent {
  usuarios: Usuario[] = [];
  rolesMap = new Map<string, string>();
  tipo: string = '';
  @ViewChildren('cardRef') cards!: QueryList<ElementRef>;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: UsuarioService,
    private enumService: EnumService
  ) {}

  ngOnInit() {
    this.loadEnums();
    this.route.params.subscribe((params) => {
      this.tipo = params['tipo'] || '';
      this.setUsuarios();
    });
  }

  ngAfterViewInit(): void {
    this.cards.changes.subscribe((cards: QueryList<ElementRef>) => {
      gsap.from(
        cards.map((c) => c.nativeElement),
        {
          opacity: 0,
          y: 40,
          duration: 0.6,
          stagger: 0.1,
          ease: 'power2.out',
        }
      );
    });
  }

  verDetalle(id: number | undefined) {
    if (id !== undefined) {
      this.router.navigate(['/app/usuario', id]);
    }
  }

  private setUsuarios() {
    switch (this.tipo) {
      case '':
        this.service
          .getAllUsuarios()
          .subscribe((s) => (this.usuarios = s.filter((u) => u.rol == 'USER')));
        break;
      case 'mods':
        this.service
          .getAllUsuarios()
          .subscribe((s) => (this.usuarios = s.filter((u) => u.rol == 'MOD')));
        break;
      case 'admins':
        this.service
          .getAllUsuarios()
          .subscribe(
            (s) => (this.usuarios = s.filter((u) => u.rol == 'ADMIN'))
          );
        break;
      default:
        this.usuarios = [];
    }
  }

  loadEnums(): void {
    this.enumService.getRoles().subscribe((roles) => {
      roles.forEach((rol) => this.rolesMap.set(rol.id, rol.nombre));
    });
  }
}
