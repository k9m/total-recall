import { Component, OnInit } from '@angular/core';
import {DocumentsService} from "./services/documents.service";
import {Document} from "./model/document";
import {DocumentPage} from "./model/document-page";

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
}
