import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { gsap } from 'gsap';
import { CustomSelectEnumComponent } from '../../../layout/custom-select-enum/custom-select-enum.component';
import { Enum } from '../../../model/enum';
import { SolicitudService } from '../../../servicios/solicitud.service';
import { Solicitud } from '../../../model/solicitud';
import { EnumService } from '../../../servicios/enum.service';

@Component({
  selector: 'app-crear-solicitud',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CustomSelectEnumComponent],
  templateUrl: './crear-solicitud.component.html',
})
export class CrearSolicitudComponent {
  solicitudForm = new FormGroup({
    titulo: new FormControl('', [Validators.required]),
    msg: new FormControl('', [Validators.required]),
    idDenuncia: new FormControl('', [Validators.required]),
    idRevisor: new FormControl(''),
    tipoSolicitud: new FormControl('', [Validators.required]),
  });

  tipos: Enum[] = [];
  solicitud: Solicitud = {} as Solicitud;

  constructor(
    private service: SolicitudService,
    private router: Router,
    private enumService: EnumService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.loadTiposSolicitud();
    this.animateForm();
  }

  crearSolicitud() {
    if (this.solicitudForm.invalid) return;
    this.solicitud = {...this.solicitudForm.value} as Solicitud
    this.service.postSolicitud(this.solicitud);
    this.router.navigate(['app/solicitudes']);
  }

  loadTiposSolicitud() {
    this.enumService.getTiposSolicitud().subscribe((tipos) => {
      this.tipos = tipos;
    })
  }

  animateForm() {
    gsap.from('form', {
      opacity: 0,
      y: 30,
      duration: 0.6,
      ease: 'power2.out',
    });
  }
}
