export class Region {
    field: string;
    left: number;
    top: number;
    width: number;
    height: number;

    constructor(field, left, top, width, height) {
        this.field = field;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }
}