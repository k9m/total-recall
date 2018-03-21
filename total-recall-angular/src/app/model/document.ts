import {DocumentPage} from "./document-page";

export class Document {
    pages: Array<DocumentPage>;

    constructor(pages) {
        this.pages = pages;
    }
}
