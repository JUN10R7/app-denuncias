import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Denuncia } from '../model/denuncia';

@Injectable({
  providedIn: 'root'
})
export class DenunciaService {

  private baseUrl = `${environment.apiUrl}/api/denuncia`;

  constructor(private http: HttpClient) { }

  getDenuncias() {
    return this.http.get<Denuncia[]>(`${this.baseUrl}/usuario`);
  }

  getDenunciaById(id: number) {
    return this.http.get<Denuncia>(`${this.baseUrl}/usuario/${id}`);
  }

  getDenunciasMod(all: boolean) {
    return this.http.get<Denuncia[]>(`${this.baseUrl}/mod/${all}`);
  }

  getAllDenuncias() {
    return this.http.get<Denuncia[]>(`${this.baseUrl}/admin`);
  }

  getDenunciaByUsuario(id: number) {
    return this.http.get<Denuncia[]>(`${this.baseUrl}/admin/usuario/${id}`);
  }

  getDenunciaByMod(id: number) {
    return this.http.get<Denuncia[]>(`${this.baseUrl}/admin/mod/${id}`);
  }

  createDenuncia(data: Partial<Denuncia>) {
    return this.http.post<Denuncia>(`${this.baseUrl}/usuario`, data);
  }

  updateDenuncia(id: number, denuncia: Denuncia) {
    return this.http.put<Denuncia>(`${this.baseUrl}/usuario/${id}`, denuncia);
  }

  updateEstadoDenuncia(id: number, estado: string) {
    return this.http.put<Denuncia>(`${this.baseUrl}/admin/${id}/estado/${id}/${estado}`, {});
  }

  asignarModDenuncia(id: number, idMod: number) {
    return this.http.put<Denuncia>(`${this.baseUrl}/admin/asignar/${id}/${idMod}`, {});
  }

  deleteDenuncia(id: number) {
    this.http.delete(`${this.baseUrl}/usuario/${id}`).subscribe();
  }

}
