import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-marcas-page',
  standalone: true,
  template: `<h1>Marcas</h1>`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MarcasPageComponent {}
