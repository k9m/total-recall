import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { RegionMarkerComponent } from './components/region-marker/region-marker.component';
import { DocumentPageComponent } from './components/document-page/document-page.component';
import {DocumentsService} from "./services/documents.service";
import { DocumentComponent } from './components/document/document.component';
import {MatButtonModule, MatExpansionModule, MatSidenavModule, MatTabsModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { DocumentPageThumbComponent } from './components/document-page-thumb/document-page-thumb.component';
import {AutoSizeInputModule} from "ngx-autosize-input";
import {HttpClientModule} from "@angular/common/http";
import {NgxJsonViewerModule} from "ngx-json-viewer";

@NgModule({
  declarations: [
    AppComponent,
    RegionMarkerComponent,
    DocumentPageComponent,
    DocumentComponent,
    DocumentPageThumbComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatTabsModule,
    MatExpansionModule,
    MatButtonModule,
    NgxJsonViewerModule,
    AutoSizeInputModule,
    HttpClientModule
  ],
  providers: [DocumentsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
