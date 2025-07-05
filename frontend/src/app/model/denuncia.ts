export interface Denuncia {
    id?: number;
    titulo: string;
    description: string;
    lugar: string;
    categoria: string;
    estado?: string;
    fechaIni?: string;
    usuario?: string;
    mod?: string;
}