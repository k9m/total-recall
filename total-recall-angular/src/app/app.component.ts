import {Component, OnInit} from '@angular/core';
import {DocumentsService} from "./services/documents.service";
import {Document} from "./model/document";
import {DocumentPage} from "./model/document-page";
import {Region} from "./model/Region";
import {RegionMarkerComponent} from "./components/region-marker/region-marker.component";
import {Mask} from "./model/mask";
import {DocumentMasks} from "./model/document-masks";
import {DocumentMasksService} from "./services/document-masks.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    documents: Array<Document>;
    documentMasks: Array<DocumentMasks>;
    currentDocument: Document;
    currentDocumentMasks: DocumentMasks;
    currentPage: DocumentPage;
    savedDocumentsData: Map<string, Object> = new Map();
    savedDocumentsDataTabActive = false;

    constructor(private documentsService: DocumentsService, private documentMasksService: DocumentMasksService) {}

    ngOnInit() {
        this.documentsService.getDocuments().subscribe(data => {
            this.documents = data._embedded.documents.map(item => new Document(
                item.documentId, item.documentType, item.fileName, Array.apply(null, {length: item.documentMetaData.nrPages})
                    .map(Number.call, Number)
                    .map(number => new DocumentPage(
                        "http://localhost:9802/document-images/" + item.documentId + "/" + number, number, new Mask([])
                    ))
            ));
        });
    }

    loadSavedDocumentMasks() {
        /*
        this.documentMasksService.getDocumentMasks().subscribe(data => {
            console.log(data);
        });
        */
    }

    updateRegion(region: Region, marker: RegionMarkerComponent) {
        if (
            marker.field !== undefined &&
            marker.left !== undefined &&
            marker.top !== undefined &&
            marker.width !== undefined &&
            marker.height !== undefined
        ) {
            region.nlp = marker.nlp;
            region.field = marker.field;
            region.left = marker.left;
            region.top = marker.top;
            region.width = marker.width;
            region.height = marker.height;

            if (this.currentDocument) {
                this.currentDocument.changed = true;
            }
            if (this.currentPage) {
                this.currentPage.changed = true;
            }
        }
    }

    addRegion(region: Region) {
        if (this.currentPage) {
            this.currentPage.mask.regions.push(region);
        }
        if (this.currentDocument) {
            this.currentDocument.changed = true;
        }
    }

    removeRegion(region: Region) {
        if (this.currentPage) {
            this.currentPage.mask.regions.splice(this.currentPage.mask.regions.indexOf(region), 1);
        }
        if (this.currentDocument) {
            this.currentDocument.changed = true;
        }
    }

    deselectDocument(document: Document) {
        if (this.currentDocument === document) {
            this.currentDocumentMasks = null;
            this.currentDocument = null;
            this.currentPage = null;
        }
    }

    selectDocument(document: Document) {
        this.currentDocumentMasks = new DocumentMasks("", new Map());
        this.currentDocument = document;
        this.currentPage = document.pages[0];
    }

    updatePageSize(page: DocumentPage, width: number, height: number) {
        page.width = width;
        page.height = height;
    }

    saveSelectedDocument(type: string) {
        if (this.currentDocument) {
            let document = this.currentDocument;

            this.documentsService.saveDocumentMasks(type, document.documentId, {
                type: document.documentType,
                pageMasking: this.currentDocument.pages.filter(page => page.changed).map(page => ({
                    pageNumber: page.pageNr,
                    pageWidth: page.width,
                    pageHeight: page.height,
                    regions: page.mask.regions.map(region => ({
                        nlp: region.nlp,
                        field: region.field,
                        x1: region.left,
                        y1: region.top,
                        x2: region.left + region.width,
                        y2: region.top + region.height
                    }))
                }))
            })
                .subscribe(() => {
                    document.changed = false;
                    document.pages.forEach(
                        page => page.changed = false
                    );
                    this.documentsService.getDocumentData(document.documentType, document.documentId).subscribe(data => {
                        this.savedDocumentsData.set(document.documentId, data);
                    });
                });
        }
    }

    saveSelectedDocumentMasks(id: string) {
        if (this.currentDocumentMasks) {
            this.currentDocument.pages.reduce((result, page) => {
                result.set(page.pageNr, page.mask);

                return result;
            }, this.currentDocumentMasks.masks);

            let data = {
                id: this.currentDocumentMasks.id,
                masks: []
            };

            this.currentDocumentMasks.masks.forEach((mask, key) => data.masks.push({
                pageNumber: key,
                regions: mask.regions.map(region => ({
                    field: region.field,
                    x1: region.left,
                    y1: region.top,
                    x2: region.left + region.width,
                    y2: region.top + region.height
                }))
            }));

            this.documentMasksService.saveDocumentMasks(this.currentDocumentMasks.id, data).subscribe(() => {
                this.loadSavedDocumentMasks();
            });
        }
    }

    tabSelected($event) {
        this.savedDocumentsDataTabActive = $event.index === 1;
    }
}
