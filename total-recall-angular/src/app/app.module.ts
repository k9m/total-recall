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
import * as hljs from 'highlightjs';
import { HighlightJsModule, HIGHLIGHT_JS } from 'angular-highlight-js';

export function highlightJsFactory() {
    return hljs;
}

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
    AutoSizeInputModule,
    HttpClientModule,
    HighlightJsModule.forRoot({
      provide: HIGHLIGHT_JS,
      useFactory: highlightJsFactory
    })
  ],
  providers: [DocumentsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
