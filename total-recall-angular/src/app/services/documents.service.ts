import { Injectable } from '@angular/core';
import {Document} from "../model/document";
import {DocumentPage} from "../model/document-page";
import {Mask} from "../model/mask";
import {Region} from "../model/Region";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class DocumentsService {

    constructor(private http: HttpClient) {
    }

    getDocuments(): Observable<any> {
        return this.http.get("http://localhost:9802/documents");
    }

    saveDocumentMasks(documentId: string, masking): Observable<any> {
        return this.http.put("http://localhost:9802/documents/" + documentId + "/masking", masking);
    }

}
