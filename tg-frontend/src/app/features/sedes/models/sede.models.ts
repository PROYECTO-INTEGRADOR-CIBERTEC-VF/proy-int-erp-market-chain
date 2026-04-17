export interface SedeRequest {
  nombre: string;
  email: string;
  gerenteNombre: string;
  direccion: string;
  ubigeo: string;
  telefono: string;
  esAlmacenCentral: boolean;
  estado: boolean;
  horarioConfig: Record<string, unknown>;
}

export interface SedeResponse {
  idSede: number;
  nombre: string;
  email: string;
  gerenteNombre: string;
  direccion: string;
  ubigeo: string;
  telefono: string;
  esAlmacenCentral: boolean;
  estado: boolean;
  horarioConfig: string;
}
