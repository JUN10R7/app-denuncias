import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Usuario } from '../model/usuario';
import { Notification } from '../model/notification';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private baseUrl = `${environment.apiUrl}/api/usuario`;

  constructor(private http: HttpClient) { }

  registerUsuario(usuario: Usuario) {
    return this.http.post<Usuario>(`${environment.apiUrl}/registro`, usuario);
  }

  getUsuario() {
    return this.http.get<Usuario>(`${this.baseUrl}`);
  }

  getUsuariosActivos() {
    return this.http.get<Usuario[]>(`${this.baseUrl}/mod`);
  }

  getAllUsuarios() {
    return this.http.get<Usuario[]>(`${this.baseUrl}/admin`);
  }

  getUsuarioById(id: number) {
    return this.http.get<Usuario>(`${this.baseUrl}/mod/${id}`);
  }

  updateUsuario(data: Partial<Usuario>) {
    return this.http.put<Usuario>(`${this.baseUrl}`, data);
  }

  activateUsuarioById(id: number, usuario: Usuario) {
    return this.http.put<Usuario>(`${this.baseUrl}/admin/${id}`, usuario);
  }

  deleteUsuario() {
    this.http.delete(`${this.baseUrl}`).subscribe({
      next: () => {
        localStorage.clear();
      }
    });
  }

  deleteUsuarioById(id: number) {
    return this.http.delete(`${this.baseUrl}/admin/${id}`);
  }

  getNotificaciones() {
    return this.http.get<Notification[]>(`${this.baseUrl}/notificaciones`);
  }

  markNotificationAsRead(id: number) {
    return this.http.put(`${this.baseUrl}/notificaciones/${id}`, {});
  }

}
