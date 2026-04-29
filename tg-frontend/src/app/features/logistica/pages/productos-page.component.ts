import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { AppCardComponent } from '../../../shared/ui/app-card/app-card.component';
import { Producto } from '../models/producto.model';
import { ProductoService } from '../services/producto.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-productos-page',
  standalone: true,
  imports: [AppCardComponent, RouterLink],
  templateUrl: './productos-page.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductosPageComponent implements OnInit {
  private readonly productoService = inject(ProductoService);

  protected readonly productos = signal<Producto[]>([]);
  protected readonly loading = signal(false);
  protected readonly errorMessage = signal('');

  ngOnInit(): void {
    this.loadProductos();
  }

  protected formatEstado(value: boolean): string {
    return value ? 'Activo' : 'Inactivo';
  }

  private loadProductos(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    this.productoService
      .getAll()
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (data) => this.productos.set(data),
        error: (error: unknown) => this.errorMessage.set(this.resolveError(error))
      });
  }

  private resolveError(error: unknown): string {
    if (error instanceof HttpErrorResponse) {
      if (error.status === 401) {
        return 'Tu sesion expiro. Inicia sesion nuevamente.';
      }

      if (error.status === 403) {
        return 'No tienes permisos suficientes para esta operacion.';
      }
    }

    return 'No se pudo cargar el listado de productos.';
  }
}
