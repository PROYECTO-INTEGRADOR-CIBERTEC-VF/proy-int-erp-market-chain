import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map, catchError } from 'rxjs';

import { Producto, ProductoRequest } from '../models/producto.model';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private readonly http = inject(HttpClient);
  private readonly url = 'http://localhost:8081/api/productos';
  // Try catalog namespace first to match other catalog services
  private readonly catalogUrl = 'http://localhost:8081/api/catalog/productos';

  // Compatible naming with other services
  getAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.catalogUrl).pipe(
      catchError(() => this.listarTodos())
    );
  }

  getById(id: number): Observable<Producto> {
    return this.http.get<unknown>(`${this.catalogUrl}/${id}`).pipe(
      map((raw) => this.toProducto(this.unwrapData(raw)))
    );
  }

  create(data: ProductoRequest, token: string): Observable<Producto> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<Producto>(this.catalogUrl, data, { headers });
  }

  update(id: number, data: ProductoRequest, token: string): Observable<Producto> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.put<Producto>(`${this.catalogUrl}/${id}`, data, { headers });
  }

  listarTodos(): Observable<Producto[]> {
    return this.http.get<unknown>(this.url).pipe(map((raw) => this.normalizeListResponse(raw)));
  }

  private normalizeListResponse(raw: unknown): Producto[] {
    const source = this.unwrapData(raw);
    if (!Array.isArray(source)) {
      return [];
    }

    return source.map((item) => this.toProducto(item));
  }

  private unwrapData(raw: unknown): unknown {
    if (raw && typeof raw === 'object' && 'data' in (raw as Record<string, unknown>)) {
      return (raw as Record<string, unknown>)['data'];
    }

    return raw;
  }

  private toProducto(raw: unknown): Producto {
    const src = (raw ?? {}) as Record<string, unknown>;

    return {
      id: this.toNumberOrNull(src['id']),
      nombreBase: this.toString(src['nombreBase'] ?? src['nombre'] ?? src['nombre_base']),
      variante: this.toNullableString(this.toString(src['variante'] ?? src['variacion'])),
      medidaValor: this.toNumberOrNull(src['medidaValor'] ?? src['medida_valor']),
      medidaUnidad: this.toNullableString(this.toString(src['medidaUnidad'] ?? src['medida_unidad'])),
      sku: this.toNullableString(this.toString(src['sku'])),
      precioCosto: this.toNumberOrNull(src['precioCosto'] ?? src['precio_costo']),
      precioVenta: this.toNumberOrNull(src['precioVenta'] ?? src['precio_venta']),
      imagenUrl: this.toNullableString(this.toString(src['imagenUrl'] ?? src['imagen_url'] ?? src['imagen'])),
      estado: this.toBoolean(src['estado']),
      nombreMarca: this.toNullableString(this.toString(src['nombreMarca'] ?? src['marca'] ?? src['marca_nombre'])),
      nombreSubCategoria: this.toNullableString(this.toString(src['nombreSubCategoria'] ?? src['subCategoria'] ?? src['subcategoria_nombre'])),
      idMarca: this.toNumberOrNull(src['idMarca'] ?? src['id_marca'] ?? src['marcaId']),
      idSubCategoria: this.toNumberOrNull(src['idSubCategoria'] ?? src['idSubcategoria'] ?? src['id_subcategoria'] ?? src['subcategoriaId'])    };
  }

  private toString(value: unknown): string {
    return typeof value === 'string' ? value : '';
  }

  private toNullableString(value: string | null | undefined): string | null {
    const normalized = value?.trim() ?? '';
    return normalized.length > 0 ? normalized : null;
  }

  private toNumberOrNull(value: unknown): number | null {
    if (value === null || value === undefined) {
      return null;
    }

    if (typeof value === 'number' && Number.isFinite(value)) {
      return value;
    }

    if (typeof value === 'string') {
      const parsed = Number(value);
      return Number.isFinite(parsed) ? parsed : null;
    }

    return null;
  }

  private toBoolean(value: unknown): boolean {
    if (typeof value === 'boolean') {
      return value;
    }

    if (typeof value === 'number') {
      return value === 1;
    }

    if (typeof value === 'string') {
      const normalized = value.trim().toLowerCase();
      if (normalized === 'true' || normalized === '1' || normalized === 'activo') {
        return true;
      }

      if (normalized === 'false' || normalized === '0' || normalized === 'inactivo') {
        return false;
      }
    }

    return false;
  }
}
