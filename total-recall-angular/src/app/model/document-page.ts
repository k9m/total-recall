import {Mask} from "./mask";

export class DocumentPage {
    pageImageUri: string;
    pageNr: number;
    mask: Mask;
    width = 0;
    height = 0;
    changed = false;

    constructor(pageImageUri, pageNr, mask) {
        this.pageImageUri = pageImageUri;
        this.pageNr = pageNr;
        this.mask = mask;
    }
}
