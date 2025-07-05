import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { EnumDTO } from '../model/enum.dto';

@Injectable({
  providedIn: 'root'
})
export class EnumService {
  private baseUrl = `${environment.apiUrl}/usuario`;

  constructor(private http: HttpClient) {}

  getRoles() {
    return this.http.get<EnumDTO[]>(`${this.baseUrl}/roles`);
  }

  getCategorias() {
    return this.http.get<EnumDTO[]>(`${this.baseUrl}/categorias`);
  }

  getEstados() {
    return this.http.get<EnumDTO[]>(`${this.baseUrl}/estados`);
  }

  getTiposSolicitud() {
    return this.http.get<EnumDTO[]>(`${this.baseUrl}/tiposSolicitud`);
  }
}
