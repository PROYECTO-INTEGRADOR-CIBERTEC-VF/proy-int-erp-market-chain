import { Routes } from '@angular/router';

export const SUBCATEGORIAS_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/sub-categorias-page.component').then(m => m.SubCategoriasPageComponent)
  }
];
