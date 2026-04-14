import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { finalize } from 'rxjs';

import { AuthService } from '../../../core/auth/services/auth.service';

interface NavItem {
  label: string;
  icon: string;
  path: string;
}

@Component({
  selector: 'app-main-layout',
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MainLayoutComponent {
  private readonly authService = inject(AuthService);

  protected readonly sidebarOpen = signal(false);
  protected readonly loggingOut = signal(false);
  protected readonly userDisplayName = signal(this.resolveUserName());
  protected readonly navItems: NavItem[] = [
    { label: 'Sedes', icon: 'store', path: '/sedes' },
    { label: 'Inventario', icon: 'inventory_2', path: '/inventario' },
    { label: 'Compras', icon: 'shopping_cart', path: '/compras' }
  ];

  protected toggleSidebar(): void {
    this.sidebarOpen.update((value) => !value);
  }

  protected closeSidebar(): void {
    this.sidebarOpen.set(false);
  }

  protected logout(): void {
    if (this.loggingOut()) {
      return;
    }

    this.loggingOut.set(true);

    this.authService
      .logout()
      .pipe(
        finalize(() => {
          this.loggingOut.set(false);
        })
      )
      .subscribe();
  }

  private resolveUserName(): string {
    const token = localStorage.getItem('token');
    if (!token) {
      return 'Usuario';
    }

    const payload = this.decodeJwtPayload(token);
    if (!payload) {
      return 'Usuario';
    }

    const candidates = [
      payload['nombreCompleto'],
      payload['name'],
      payload['email'],
      payload['sub']
    ];

    for (const value of candidates) {
      if (typeof value === 'string' && value.trim().length > 0) {
        return value;
      }
    }

    return 'Usuario';
  }

  private decodeJwtPayload(token: string): Record<string, unknown> | null {
    const parts = token.split('.');
    if (parts.length < 2) {
      return null;
    }

    try {
      const base64 = parts[1].replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = atob(base64);
      const data = JSON.parse(jsonPayload) as Record<string, unknown>;
      return data;
    } catch {
      return null;
    }
  }
}
