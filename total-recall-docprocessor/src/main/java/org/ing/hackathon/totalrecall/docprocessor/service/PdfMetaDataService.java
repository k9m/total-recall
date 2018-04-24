package org.ing.hackathon.totalrecall.docprocessor.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.DocumentMetaData;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.DocumentPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class PdfMetaDataService {

  @Autowired
  public PdfToImageConverter pdfToImageConverter;

  public Document createDocumentProfile(
          final String documentId,
            final String documentType,
            final String fileName,
            final Long clientId,
          final byte[] documentBytes){

    PDDocument pdDocument = null;
    Document document = null;
    try {
      try {
        pdDocument = PDDocument.load(documentBytes);
        final PDDocumentInformation pdDocumentInformation = pdDocument.getDocumentInformation();
        final int nrPages = pdDocument.getNumberOfPages();

        document = Document.builder()
                .documentId(documentId)
                .documentType(documentType)
                .fileName(fileName)
                .clientId(clientId)
                .document(documentBytes)
                .build();

        document.setDocumentMetaData(DocumentMetaData.builder()
                .nrPages(pdDocument.getNumberOfPages())
                .author(pdDocumentInformation.getAuthor())
                .document(document)
                .build());

        final ArrayList<DocumentPages> documentPages = new ArrayList<>();
        log.info("Generating JPG image pages for document [{}]", document.getFileName());
        for (int i = 0; i < nrPages; i++) {
          log.info("Generating JPG image for page [{}]", i);
          documentPages.add(DocumentPages.builder()
                  .id(UUID.randomUUID().toString())
                  .pageNr(i)
                  .pageImage(pdfToImageConverter.convert(pdDocument, i))
                  .document(document)
                  .build());
        }

        document.setDocumentPages(documentPages);

      } catch (IOException e) {
        throw new PdfProcessingErrorException(e);
      } finally {
        try {
          if (pdDocument != null) {
            pdDocument.close();
          }
        } catch (IOException e) {
          log.error("Failed to close PDDocument", e);
        }
      }
    }
    finally{
      return document;
    }
  }

}
