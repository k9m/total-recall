import {Mask} from "./mask";

export class DocumentPage {
    pageImageUri: string;
    pageNr: number;
    mask: Mask;

    constructor(pageImageUri, pageNr, mask) {
        this.pageImageUri = pageImageUri;
        this.pageNr = pageNr;
        this.mask = mask;
    }
}
