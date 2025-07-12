export interface Solicitud {
  id?: number;
  titulo: string;
  msg: string;
  respuesta: string;
  idDenuncia?: string;
  autor?: string;
  idAutor?: number;
  revisor?: string;
  idRevisor?: number;
  estado?: string;
  tipoSolicitud?: string;
  createdDate?: string;
  endDate?: string;
};