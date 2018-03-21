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
        new DocumentPage("/assets/document.png", 1, new Mask([
        ])),
        new DocumentPage("/assets/document.png", 2, new Mask([
        ]))
    ])
  }

}
