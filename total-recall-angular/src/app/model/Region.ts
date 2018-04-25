export class Region {
    nlp: boolean;
    field: string;
    left: number;
    top: number;
    width: number;
    height: number;

    constructor(nlp, field, left, top, width, height) {
        this.nlp = nlp;
        this.field = field;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }
}