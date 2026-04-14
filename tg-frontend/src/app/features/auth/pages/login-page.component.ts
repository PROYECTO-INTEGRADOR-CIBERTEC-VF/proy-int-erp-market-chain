import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AuthService } from '../../../core/auth/services/auth.service';
import { AppButtonComponent } from '../../../shared/ui/app-button/app-button.component';
import { AppCardComponent } from '../../../shared/ui/app-card/app-card.component';
import { AppInputComponent } from '../../../shared/ui/app-input/app-input.component';

@Component({
  selector: 'app-login-page',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    AppButtonComponent,
    AppCardComponent,
    AppInputComponent
  ],
  templateUrl: './login-page.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginPageComponent {
  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly loading = signal(false);
  protected readonly errorMessage = signal('');

  protected readonly form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4)]]
  });

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const email = this.form.controls.email.value;
    const password = this.form.controls.password.value;

    if (!email || !password) {
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    this.authService
      .login({ email, password })
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: () => {
          void this.router.navigateByUrl('/sedes');
        },
        error: () => {
          this.errorMessage.set('No se pudo iniciar sesion. Verifica tus credenciales.');
        }
      });
  }
}
