import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MarcaResponse } from '../models/marca.models';

interface AuthResponse {
  token: string;
  rol: string;
}

@Injectable({ providedIn: 'root' })
export class MarcasApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8081/api/catalog/marcas';

  // Autenticación: devuelve { token, rol }
  authLogin(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('http://localhost:8080/api/auth/login', { email, password });
  }

  listAll(): Observable<MarcaResponse[]> {
    return this.http.get<MarcaResponse[]>('http://localhost:8081/api/catalog/marcas');
  }

  // Generar código sugerido (respuesta text/plain)
  generarCodigo(nombre: string): Observable<string> {
    return this.http.get('http://localhost:8081/api/catalog/marcas/generar-codigo', {
      params: { nombre },
      responseType: 'text'
    });
  }

  // Crear marca (usa token ADMIN en Authorization)
  crearMarca(nombre: string, token: string): Observable<MarcaResponse> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<MarcaResponse>('http://localhost:8081/api/catalog/marcas', { nombre }, { headers });
  }

  getById(id: number): Observable<MarcaResponse> {
    return this.http.get<MarcaResponse>(`http://localhost:8081/api/catalog/marcas/${id}`);
  }

  // Actualizar marca (envía codigoMarca para evitar que quede NULL)
  actualizarMarca(id: number, data: { nombre: string; codigoMarca?: string | null }, token: string): Observable<MarcaResponse> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' });
    return this.http.put<MarcaResponse>(`http://localhost:8081/api/catalog/marcas/${id}`, data, { headers });
  }

  // Borrar marca (ADMIN)
  borrarMarca(id: number, token: string): Observable<void> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.delete<void>(`http://localhost:8081/api/catalog/marcas/${id}`, { headers });
  }
}
