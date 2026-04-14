import { Routes } from '@angular/router';

import { authChildGuard, authGuard } from './core/auth/guards/auth.guard';

export const routes: Routes = [
	{
		path: '',
		pathMatch: 'full',
		redirectTo: 'login'
	},
	{
		path: 'login',
		loadComponent: () =>
			import('./features/auth/pages/login-page.component').then(
				(m) => m.LoginPageComponent
			)
	},
	{
		path: '',
		canActivate: [authGuard],
		canActivateChild: [authChildGuard],
		loadComponent: () =>
			import('./shared/components/layout/main-layout.component').then(
				(m) => m.MainLayoutComponent
			),
		children: [
			{
				path: 'sedes',
				loadChildren: () =>
					import('./features/sedes/sedes.routes').then((m) => m.SEDES_ROUTES)
			},
			{
				path: 'inventario',
				loadChildren: () =>
					import('./features/inventario/inventario.routes').then(
						(m) => m.INVENTARIO_ROUTES
					)
			},
			{
				path: 'compras',
				loadChildren: () =>
					import('./features/compras/compras.routes').then((m) => m.COMPRAS_ROUTES)
			}
		]
	},
	{
		path: '**',
		redirectTo: 'login'
	}
];
