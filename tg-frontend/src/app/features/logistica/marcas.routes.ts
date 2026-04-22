import { Routes } from '@angular/router';

export const MARCAS_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/marcas-page.component').then(m => m.MarcasPageComponent)
  }
];
