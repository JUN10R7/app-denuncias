import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Enum } from '../model/enum';

@Injectable({
  providedIn: 'root',
})
export class EnumService {
  private baseUrl = `${environment.apiUrl}/api/enum`;

  constructor(private http: HttpClient) {}

  getRoles() {
    return this.http.get<Enum[]>(`${this.baseUrl}/roles`);
  }

  getCategorias() {
    return this.http.get<Enum[]>(`${this.baseUrl}/categorias`);
  }

  getEstados() {
    return this.http.get<Enum[]>(`${this.baseUrl}/estados`);
  }

  getTiposSolicitud() {
    return this.http.get<Enum[]>(`${this.baseUrl}/tiposSolicitud`);
  }

}
