import { Injectable } from '@angular/core';
import {Document} from "../model/document";
import {DocumentPage} from "../model/document-page";
import {Mask} from "../model/mask";
import {Region} from "../model/Region";

@Injectable()
export class DocumentsService {

  constructor() { }

  getDocument() {
    return new Document([
        new DocumentPage("http://localhost:9802/docprocessor/test1", 1, new Mask([
            new Region("Title", 20, 20, 100, 30)
        ])),
        new DocumentPage("http://localhost:9802/docprocessor/test1", 2, new Mask([
            new Region("Title", 20, 20, 100, 30)
        ]))
    ])
  }

}
