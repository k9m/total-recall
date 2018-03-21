import {Component, ElementRef, EventEmitter, HostListener, Input, Output} from '@angular/core';

class DraggingState {
    originalLeft: number;
    originalTop: number;
    mouseStartLeft: number;
    mouseStartTop: number;

    constructor(originalLeft, originalTop, mouseStartLeft, mouseStartTop) {
        this.originalLeft = originalLeft;
        this.originalTop = originalTop;
        this.mouseStartLeft = mouseStartLeft;
        this.mouseStartTop = mouseStartTop;
    }
}

class ResizingState {
    originalLeft: number;
    originalTop: number;
    originalWidth: number;
    originalHeight: number;
    mouseStartLeft: number;
    mouseStartTop: number;
    resizingDirection: string;

    constructor(originalLeft, originalTop, originalWidth, originalHeight, mouseStartLeft, mouseStartTop, resizingDirection) {
        this.originalLeft = originalLeft;
        this.originalTop = originalTop;
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.mouseStartLeft = mouseStartLeft;
        this.mouseStartTop = mouseStartTop;
        this.resizingDirection = resizingDirection;
    }
}

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
    @Output() change = new EventEmitter<any>();
    draggingState: DraggingState;
    resizingState: ResizingState;

    startResize(event: MouseEvent, resizingDirection: string) {
        if (!this.draggingState) {
            this.resizingState = new ResizingState(
                this.left,
                this.top,
                this.width,
                this.height,
                event.clientX,
                event.clientY,
                resizingDirection
            );

            event.preventDefault();
        }
    }

    startDragging(event: MouseEvent) {
        if (!this.resizingState) {
            this.draggingState = new DraggingState(
                this.left,
                this.top,
                event.clientX,
                event.clientY
            );

            event.preventDefault();
        }
    }

    @HostListener('document:mousemove', ['$event'])
    mouseMove(event: MouseEvent) {
        if (this.resizingState) {
            let resizingDirections: Array<string> = this.resizingState.resizingDirection.split("-");

            if (resizingDirections.indexOf("top") !== -1) {
                this.top = Math.min(
                    this.resizingState.originalTop + (event.clientY - this.resizingState.mouseStartTop),
                    this.resizingState.originalTop + this.resizingState.originalHeight
                );
                this.height = Math.max(
                    this.resizingState.originalHeight - (event.clientY - this.resizingState.mouseStartTop),
                    0
                );
            }
            if (resizingDirections.indexOf("bottom") !== -1) {
                this.height = Math.max(
                    this.resizingState.originalHeight + (event.clientY - this.resizingState.mouseStartTop),
                    0
                );
            }
            if (resizingDirections.indexOf("left") !== -1) {
                this.left = Math.min(
                    this.resizingState.originalLeft + (event.clientX - this.resizingState.mouseStartLeft),
                    this.resizingState.originalLeft + this.resizingState.originalHeight
                );
                this.width = Math.max(
                    this.resizingState.originalWidth - (event.clientX - this.resizingState.mouseStartLeft),
                    0
                );
            }
            if (resizingDirections.indexOf("right") !== -1) {
                this.width = Math.max(
                    this.resizingState.originalWidth + (event.clientX - this.resizingState.mouseStartLeft),
                    0
                );
            }
        }

        if (this.draggingState) {
            this.left = this.draggingState.originalLeft + (event.clientX - this.draggingState.mouseStartLeft);
            this.top = this.draggingState.originalTop + (event.clientY - this.draggingState.mouseStartTop);
        }
    }

    @HostListener('document:mouseup', ['$event'])
    mouseEnd() {
        this.resizingState = null;
        this.draggingState = null;
        this.change.emit(this);
    }

    updateField(field) {
        this.field = field;
        this.change.emit(this);
    }
}
