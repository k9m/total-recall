import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-document-page-thumb',
  templateUrl: './document-page-thumb.component.html',
  styleUrls: ['./document-page-thumb.component.css']
})
export class DocumentPageThumbComponent {
    @Input() pageNr: number;
    @Input() pageImageUri: string;
    @Output() select = new EventEmitter<any>()

    onSelect() {
        this.select.emit({});
    }
}
