import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class DocumentsService {

    constructor(private http: HttpClient) {
    }

    getDocuments(): Observable<any> {
        return this.http.get("http://localhost:9802/documents");
    }

    getDocumentData(documentType: string, documentId: string): Observable<any> {
        return this.http.get("http://localhost:9801/get/" + documentType + "/" + documentId);
    }

    saveDocumentMasks(type: string, documentId: string, masking): Observable<any> {
        return this.http.put("http://localhost:9802/documents/" + documentId + "/" + type, masking);
    }

}
