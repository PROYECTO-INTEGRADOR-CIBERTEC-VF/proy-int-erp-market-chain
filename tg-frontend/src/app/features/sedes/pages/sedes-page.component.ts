import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AuthService } from '../../../core/auth/services/auth.service';
import { AdminApprovalModalComponent } from '../../../shared/components/admin-approval-modal/admin-approval-modal.component';
import { AppCardComponent } from '../../../shared/ui/app-card/app-card.component';
import { SedeResponse } from '../models/sede.models';
import { SedesService } from '../services/sedes.service';

@Component({
  selector: 'app-sedes-page',
  imports: [RouterLink, AppCardComponent, AdminApprovalModalComponent],
  templateUrl: './sedes-page.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SedesPageComponent {
  private readonly sedesService = inject(SedesService);
  private readonly authService = inject(AuthService);

  protected readonly sedes = signal<SedeResponse[]>([]);
  protected readonly loading = signal(false);
  protected readonly deletingId = signal<number | null>(null);
  protected readonly pendingDeleteId = signal<number | null>(null);
  protected readonly approvalOpen = signal(false);
  protected readonly approvalLoading = signal(false);
  protected readonly approvalError = signal('');
  protected readonly errorMessage = signal('');

  constructor() {
    this.loadSedes();
  }

  protected requestDeleteSede(id: number): void {
    this.pendingDeleteId.set(id);
    this.approvalError.set('');
    this.approvalOpen.set(true);
  }

  protected closeApprovalModal(): void {
    this.approvalOpen.set(false);
    this.approvalLoading.set(false);
    this.approvalError.set('');
    this.pendingDeleteId.set(null);
  }

  protected approveDelete(credentials: { email: string; password: string }): void {
    const id = this.pendingDeleteId();
    if (!id) {
      this.closeApprovalModal();
      return;
    }

    this.approvalLoading.set(true);
    this.approvalError.set('');

    this.authService.validateAdminCredentials(credentials).subscribe({
      next: (adminSession) => this.deleteSede(id, adminSession.token),
      error: (error: unknown) => {
        this.approvalLoading.set(false);
        this.approvalError.set(this.resolveAdminApprovalError(error));
      }
    });
  }

  private deleteSede(id: number, adminToken: string): void {
    if (!adminToken || adminToken.trim().length === 0) {
      this.approvalLoading.set(false);
      this.approvalError.set('No se pudo obtener el token del administrador.');
      return;
    }

    this.deletingId.set(id);
    this.errorMessage.set('');

    this.sedesService
      .delete(id, adminToken)
      .pipe(
        finalize(() => {
          this.deletingId.set(null);
          this.approvalLoading.set(false);
          this.approvalOpen.set(false);
          this.pendingDeleteId.set(null);
        })
      )
      .subscribe({
        next: () => {
          this.sedes.update((current) => current.filter((item) => item.idSede !== id));
        },
        error: (error: unknown) => {
          this.errorMessage.set(this.resolveError(error));
        }
      });
  }

  protected formatEstado(value: boolean): string {
    return value ? 'Activo' : 'Inactivo';
  }

  protected formatAlmacenCentral(value: boolean): string {
    return value ? 'Si' : 'No';
  }

  protected displayEmpty(value: string): string {
    return value.trim().length > 0 ? value : '';
  }

  private loadSedes(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    this.sedesService
      .getAll()
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (data) => {
          this.sedes.set(data);
        },
        error: (error: unknown) => {
          this.errorMessage.set(this.resolveError(error));
        }
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

    return 'No se pudo cargar el listado de sedes.';
  }

  private resolveAdminApprovalError(error: unknown): string {
    if (error instanceof HttpErrorResponse) {
      if (error.status === 401) {
        return 'Credenciales admin incorrectas.';
      }

      if (error.status === 403) {
        return 'La cuenta admin no tiene permisos para aprobar.';
      }
    }

    if (error instanceof Error && error.message.length > 0) {
      return error.message;
    }

    return 'No se pudo validar la aprobacion de administrador.';
  }
}
