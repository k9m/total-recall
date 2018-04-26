import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {DocumentMasks} from "../model/document-masks";

@Injectable()
export class DocumentMasksService {

    constructor(private http: HttpClient) {
    }

    getDocumentMasks(): Observable<any> {
        return this.http.get("http://localhost:9801/masks");
    }

    saveDocumentMasks(id, documentMasks): Observable<any> {
        return this.http.put("http://localhost:9801/masks/" + String(id), documentMasks);
    }
}
