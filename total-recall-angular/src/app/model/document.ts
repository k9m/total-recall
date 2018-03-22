import {DocumentPage} from "./document-page";

export class Document {
    documentId: string;
    fileName: string;
    pages: Array<DocumentPage>;
    changed = false;

    constructor(documentId, fileName, pages) {
        this.documentId = documentId;
        this.fileName = fileName;
        this.pages = pages;
    }
}
