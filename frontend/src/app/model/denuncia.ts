export interface Denuncia {
    id?: number;
    titulo: string;
    description: string;
    lugar: string;
    categoria: string;
    estado?: string;
    fecha?: string;
    usuario?: string;
    username?: string;
    idUsuario?: number;
    mod?: string;
    idMod?: number;
}