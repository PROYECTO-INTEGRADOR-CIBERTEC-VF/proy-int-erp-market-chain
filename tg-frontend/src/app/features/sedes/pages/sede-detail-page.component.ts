import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AppCardComponent } from '../../../shared/ui/app-card/app-card.component';
import { SedeResponse } from '../models/sede.models';
import { SedesService } from '../services/sedes.service';

@Component({
  selector: 'app-sede-detail-page',
  imports: [RouterLink, AppCardComponent],
  templateUrl: './sede-detail-page.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SedeDetailPageComponent {
  private readonly sedesService = inject(SedesService);
  private readonly route = inject(ActivatedRoute);
  private readonly dayOrder = [
    'lunes',
    'martes',
    'miercoles',
    'jueves',
    'viernes',
    'sabado',
    'domingo'
  ] as const;
  private readonly dayLabels: Record<(typeof this.dayOrder)[number], string> = {
    lunes: 'Lunes',
    martes: 'Martes',
    miercoles: 'Miercoles',
    jueves: 'Jueves',
    viernes: 'Viernes',
    sabado: 'Sabado',
    domingo: 'Domingo'
  };

  protected readonly sedeId = Number(this.route.snapshot.paramMap.get('id'));
  protected readonly sede = signal<SedeResponse | null>(null);
  protected readonly loading = signal(false);
  protected readonly errorMessage = signal('');
  protected readonly horarioRows = computed(() => this.getHorarioRows(this.sede()?.horarioConfig));

  constructor() {
    this.loadSede();
  }

  private loadSede(): void {
    if (!Number.isFinite(this.sedeId)) {
      this.errorMessage.set('El id de la sede no es valido.');
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    this.sedesService
      .getById(this.sedeId)
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (result) => {
          this.sede.set(result);
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
        return 'No tienes permisos para ver esta sede.';
      }

      if (error.status === 404) {
        return 'La sede solicitada no existe.';
      }
    }

    return 'No se pudo cargar el detalle de sede.';
  }

  private getHorarioRows(horarioConfig: string | undefined): HorarioRow[] {
    if (!horarioConfig) {
      return [];
    }

    try {
      const parsed = JSON.parse(horarioConfig) as Partial<Record<(typeof this.dayOrder)[number], HorarioDiaConfig>>;

      return this.dayOrder
        .filter((day) => parsed[day] !== undefined)
        .map((day) => {
          const config = parsed[day] as HorarioDiaConfig;

          if (config.cerrado) {
            return {
              day,
              dayLabel: this.dayLabels[day],
              value: 'Cerrado'
            };
          }

          const apertura = config.apertura ?? '--:--';
          const cierre = config.cierre ?? '--:--';

          return {
            day,
            dayLabel: this.dayLabels[day],
            value: `${apertura} - ${cierre}`
          };
        });
    } catch {
      return [
        {
          day: 'lunes',
          dayLabel: 'Horario',
          value: horarioConfig
        }
      ];
    }
  }
}

interface HorarioDiaConfig {
  cerrado?: boolean;
  apertura?: string;
  cierre?: string;
}

interface HorarioRow {
  day: string;
  dayLabel: string;
  value: string;
}
