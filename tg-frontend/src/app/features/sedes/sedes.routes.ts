import { Routes } from '@angular/router';

export const SEDES_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/sedes-page.component').then((m) => m.SedesPageComponent)
  },
  {
    path: ':id',
    loadComponent: () =>
      import('./pages/sede-detail-page.component').then(
        (m) => m.SedeDetailPageComponent
      )
  }
];
