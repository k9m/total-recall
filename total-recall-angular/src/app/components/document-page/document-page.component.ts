import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-document-page',
  templateUrl: './document-page.component.html',
  styleUrls: ['./document-page.component.css']
})
export class DocumentPageComponent {
  @Input() imageUri: string;
}
