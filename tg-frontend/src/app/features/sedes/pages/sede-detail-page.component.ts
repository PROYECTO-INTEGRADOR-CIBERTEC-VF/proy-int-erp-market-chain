import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AppCardComponent } from '../../../shared/ui/app-card/app-card.component';

@Component({
  selector: 'app-sede-detail-page',
  imports: [RouterLink, AppCardComponent],
  templateUrl: './sede-detail-page.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SedeDetailPageComponent {
  private readonly route = inject(ActivatedRoute);

  protected readonly sedeId = this.route.snapshot.paramMap.get('id');
}
