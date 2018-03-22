import {DocumentPage} from "./document-page";

export class Document {
    documentId: string;
    documentType: string;
    fileName: string;
    pages: Array<DocumentPage>;
    changed = false;

    constructor(documentId, documentType, fileName, pages) {
        this.documentId = documentId;
        this.documentType = documentType;
        this.fileName = fileName;
        this.pages = pages;
    }
}
