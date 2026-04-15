import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { inject } from '@angular/core';

import { LoginRequest } from '../../../core/auth/models/auth.models';
import { AppButtonComponent } from '../../ui/app-button/app-button.component';
import { AppCardComponent } from '../../ui/app-card/app-card.component';
import { AppInputComponent } from '../../ui/app-input/app-input.component';

@Component({
  selector: 'app-admin-approval-modal',
  imports: [ReactiveFormsModule, AppButtonComponent, AppCardComponent, AppInputComponent],
  templateUrl: './admin-approval-modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    '(keydown.escape)': 'onCancel()'
  }
})
export class AdminApprovalModalComponent {
  private readonly formBuilder = inject(FormBuilder);

  readonly visible = input(false);
  readonly title = input('Aprobacion de administrador');
  readonly message = input('Ingresa credenciales de administrador para continuar.');
  readonly confirmLabel = input('Aprobar');
  readonly loading = input(false);
  readonly errorMessage = input('');

  readonly approved = output<LoginRequest>();
  readonly cancelled = output<void>();

  protected readonly form = this.formBuilder.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(4)]]
  });

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const email = this.form.controls.email.value?.trim() ?? '';
    const password = this.form.controls.password.value ?? '';

    if (!email || !password) {
      return;
    }

    this.approved.emit({ email, password });
  }

  protected onCancel(): void {
    this.form.reset({ email: '', password: '' });
    this.cancelled.emit();
  }
}
