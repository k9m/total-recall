import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-region-marker',
  templateUrl: './region-marker.component.html',
  styleUrls: ['./region-marker.component.css']
})
export class RegionMarkerComponent {
  @Input() left: number;
  @Input() top: number;
  @Input() width: number;
  @Input() height: number;
  @Input() field: string;
}
