export interface LoginRequest {
  email: string;
  password: string;
}

export interface SessionUser {
  userId: number;
  email: string;
  nombreCompleto: string;
  rol: string;
}

export interface LoginResponse {
  token: string;
  userId: number;
  email: string;
  nombreCompleto: string;
  rol: string;
}
