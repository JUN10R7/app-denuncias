import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { UsuarioService } from '../../../servicios/usuario.service';
import { ActivatedRoute } from '@angular/router';
import { EnumService } from '../../../servicios/enum.service';
import { Usuario } from '../../../model/usuario';
import { CommonModule } from '@angular/common';
import gsap from 'gsap';

@Component({
  selector: 'app-detalle-usuario.component',
  imports: [CommonModule],
  templateUrl: './detalle-usuario.component.html',
  styleUrl: './detalle-usuario.component.scss',
})
export class DetalleUsuarioComponent implements AfterViewInit {
  usuario: Usuario = {} as Usuario;
  id: string = '';
  rolesMap = new Map<string, string>();

  @ViewChild('usuarioContainer', { static: false }) containerRef!: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private service: UsuarioService,
    private enumService: EnumService,
  ) {}

  ngAfterViewInit() {
    gsap.from(this.containerRef.nativeElement, {
      duration: 0.8,
      opacity: 0,
      y: 40,
      ease: 'power3.out',
    });
  }

  ngOnInit() {
    this.loadRoles();
    this.route.params.subscribe((params) => {
      this.id = params['id'] || '';
      this.setUsuario();
    });
    gsap.from('#perfilContainer', {
      opacity: 0,
      y: 50,
      duration: 0.8,
      ease: 'power2.out',
    });
  }

  setUsuario() {
    this.service.getUsuarioById(Number(this.id)).subscribe((usuario) => {
      this.usuario = usuario;
    });
  }

  loadRoles() {
    this.enumService.getRoles().subscribe((roles) => {
      roles.forEach((rol) => this.rolesMap.set(rol.id, rol.nombre));
    });
  }
}
