import { Routes } from '@angular/router';

export const CATEGORIAS_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/categorias-page.component').then(m => m.CategoriasPageComponent)
  }
];
