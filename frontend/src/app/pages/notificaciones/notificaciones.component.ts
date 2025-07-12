import {
  Component,
  ElementRef,
  QueryList,
  ViewChildren,
  AfterViewInit,
} from '@angular/core';
import { UsuarioService } from '../../servicios/usuario.service';
import { Notification } from '../../model/notification';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import gsap from 'gsap';

@Component({
  selector: 'app-notificaciones.component',
  imports: [CommonModule],
  templateUrl: './notificaciones.component.html',
  styleUrl: './notificaciones.component.scss',
})
export class NotificacionesComponent {
  notificaciones: Notification[] = [];
  @ViewChildren('cardRef') cards!: QueryList<ElementRef>;

  constructor(private service: UsuarioService, private router: Router) {}

  ngOnInit() {
    this.cargarNotificaciones();
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

  private cargarNotificaciones() {
    this.service.getNotificaciones().subscribe((notificaciones) => {
      this.notificaciones = notificaciones;
      notificaciones.forEach((noti) => {
        if (!noti.read && noti.id !== undefined) {
          this.service.markNotificationAsRead(noti.id).subscribe();
        }
      });
    });
  }

  irDetalle(noti: Notification): void {
    if (noti.idSolicitud) {
      this.router.navigate(['/app/solicitud', noti.idSolicitud]);
    } else if (noti.idDenuncia) {
      this.router.navigate(['/app/denuncias/detalle', noti.idDenuncia]);
    } 
  }
}
