import { Component } from '@angular/core';
import { DenunciaService } from '../../../servicios/denuncia.service';
import { Denuncia } from '../../../model/denuncia';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { EnumService } from '../../../servicios/enum.service';
import { CommonModule } from '@angular/common';
import { Enum } from '../../../model/enum';
import { gsap } from 'gsap';
import { CustomSelectEnumComponent } from '../../../layout/custom-select-enum/custom-select-enum.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NotExpr } from '@angular/compiler';

@Component({
  selector: 'app-crear-denuncia.component',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, CustomSelectEnumComponent],
  templateUrl: './crear-denuncia.component.html',
  styleUrl: './crear-denuncia.component.scss',
})
export class CrearDenunciaComponent {
  denuncia: Denuncia = {} as Denuncia;
  categorias: Enum[] = [];
  id: string = '';

  denunciaForm = new FormGroup({
    titulo: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    lugar: new FormControl('', [Validators.required]),
    categoria: new FormControl('', [Validators.required]),
  });

  constructor(
    private service: DenunciaService,
    private enumService: EnumService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.loadCategorias();
    this.route.params.subscribe((params) => {
      this.id = params['id'] || '';
      this.cargarForm();
    });
    this.animateForm();
  }

  cargarForm() {
    if (!this.id) return;
    this.service.getDenunciaById(Number(this.id)).subscribe((denuncia) => {
      this.denuncia = denuncia;
      this.denunciaForm.patchValue({
        titulo: denuncia.titulo,
        description: denuncia.description,
        lugar: denuncia.lugar,
        categoria: denuncia.categoria,
      });
    });
  }

  crearDenuncia() {
    if (this.denunciaForm.invalid) return;
    this.denuncia = { ...this.denunciaForm.value } as Denuncia;
    this.service.createDenuncia(this.denuncia).subscribe({
      next: (denuncia) => {
        gsap.to('form', {
          opacity: 0,
          duration: 0.4,
          onComplete: () => {
            this.router.navigate([
              '/app/denuncias/detalle',
              denuncia.id?.toString(),
            ]);
          },
        });
      },
      error: (err) => {
        console.log(err);
        gsap.to('form', { opacity: 1, pointerEvents: 'auto' });
      },
    });
  }

  guardarDenuncia() {
    if (this.denunciaForm.invalid) return;
    this.denuncia = { ...this.denunciaForm.value } as Denuncia;
    this.service.updateDenuncia(Number(this.id), this.denuncia).subscribe({
      next: (denuncia) => {
        gsap.to('form', {
          opacity: 0,
          duration: 0.4,
          onComplete: () => {
            this.router.navigate([
              '/app/denuncias/detalle',
              denuncia.id?.toString(),
            ]);
          },
        });
      },
      error: (err) => {
        console.log(err);
        gsap.to('form', { opacity: 1, pointerEvents: 'auto' });
      },
    });
  }

  loadCategorias(): void {
    this.enumService
      .getCategorias()
      .subscribe((categorias) => (this.categorias = categorias));
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
