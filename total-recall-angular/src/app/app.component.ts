import {Component, OnInit} from '@angular/core';
import {DocumentsService} from "./services/documents.service";
import {Document} from "./model/document";
import {DocumentPage} from "./model/document-page";
import {Region} from "./model/Region";
import {RegionMarkerComponent} from "./components/region-marker/region-marker.component";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    document: Document;
    currentPage: DocumentPage;

    constructor(private documentsService: DocumentsService) {}

    ngOnInit() {
        this.document = this.documentsService.getDocument();
        this.currentPage = this.document.pages[0];
    }

    updateRegion(region: Region, marker: RegionMarkerComponent) {
        region.field = marker.field;
        region.left = marker.left;
        region.top = marker.top;
        region.width = marker.width;
        region.height = marker.height;
    }

    addRegion(region: Region) {
        this.currentPage.mask.regions.push(region);
    }
}
