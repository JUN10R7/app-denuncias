export interface Notification {
  id?: number;
  titulo: string;
  message: string;
  read: boolean;
  fecha?: string;
  denunciaTitulo?: string;
  solicitudTitulo?: string;
}