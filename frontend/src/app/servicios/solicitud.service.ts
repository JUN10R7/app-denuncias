import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Solicitud } from '../model/solicitud';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {

  private baseUrl = `${environment.apiUrl}/api/solicitud`;
  
  constructor(private http: HttpClient) { }

  getSolicitud(id: number) {
    return this.http.get<Solicitud>(`${this.baseUrl}/${id}`);
  }

  getSolicitudes() {
    return this.http.get<Solicitud[]>(`${this.baseUrl}/listar`);
  }

  getSolicitudesAdmin() {
    return this.http.get<Solicitud[]>(`${this.baseUrl}/admin/listar`);
  }

  getSolicitudesByDenuncia(id: number) {
    return this.http.get<Solicitud[]>(`${this.baseUrl}/admin/denuncia/${id}`);
  }

  postSolicitud(solicitud: Solicitud) {
    this.http.post(`${this.baseUrl}`, solicitud).subscribe();
  }

  revisarSolicitud(id: number, aprobado: boolean, mensajeRespuesta: string) {
    const body = { id, aprobado, mensajeRespuesta };
    return this.http.put<Solicitud>(`${this.baseUrl}`, body);
  }

}
