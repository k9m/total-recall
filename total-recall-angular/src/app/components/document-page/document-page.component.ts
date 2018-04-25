import {Component, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {Region} from "../../model/Region";
import * as jQuery from "jquery";

class RegionCreationState {
    mouseStartLeft: number;
    mouseStartTop: number;
    newRegion: Region;

    constructor(mouseStartLeft, mouseStartTop, newRegion) {
        this.mouseStartLeft = mouseStartLeft;
        this.mouseStartTop = mouseStartTop;
        this.newRegion = newRegion;
    }
}

@Component({
    selector: 'app-document-page',
    templateUrl: './document-page.component.html',
    styleUrls: ['./document-page.component.css']
})
export class DocumentPageComponent {
    @Input() imageUri: string;
    @Output() newRegion = new EventEmitter<any>();
    @Output() load = new EventEmitter<any>();
    regionCreationState: RegionCreationState;

    startCreating(event: MouseEvent) {
        let position = jQuery(event.srcElement).offset();
        let region = new Region(false, "", event.clientX - position.left, event.clientY - position.top, 0, 0);

        this.regionCreationState = new RegionCreationState(
            event.clientX,
            event.clientY,
            region
        );
        this.newRegion.emit(region);

        event.preventDefault();
    }

    @HostListener('document:mousemove', ['$event'])
    mouseMove(event: MouseEvent) {
        if (this.regionCreationState) {
            this.regionCreationState.newRegion.height = Math.max(
                event.clientY - this.regionCreationState.mouseStartTop,
                0
            );
            this.regionCreationState.newRegion.width = Math.max(
                event.clientX - this.regionCreationState.mouseStartLeft,
                0
            );
        }
    }

    @HostListener('document:mouseup', ['$event'])
    mouseEnd() {
        this.regionCreationState = null;
    }

    loaded($event) {
        this.load.emit({
            width: $event.target.offsetWidth,
            height: $event.target.offsetHeight
        });
    }
}
