export interface Solicitud {
  id?: number;
  titulo: string;
  msg: string;
  respuesta: string;
  denuncia?: string;
  autor?: string;
  revisor?: string;
  estado?: string;
  tipoSolicitud?: string;
  createdDate?: string;
  endDate?: string;
};