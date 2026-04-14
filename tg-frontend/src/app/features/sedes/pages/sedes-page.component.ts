import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AppCardComponent } from '../../../shared/ui/app-card/app-card.component';

@Component({
  selector: 'app-sedes-page',
  imports: [RouterLink, AppCardComponent],
  templateUrl: './sedes-page.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SedesPageComponent {}
