package org.ing.hackathon.totalrecall.docprocessor.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.DocumentMetaData;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PdfMetaDataService {

  public DocumentMetaData getNrPages(final byte[] sample){
    PDDocument document = null;
    try {
      document = PDDocument.load(sample);
      final PDDocumentInformation pdDocumentInformation = document.getDocumentInformation();

      return DocumentMetaData.builder()
                .nrPages(document.getNumberOfPages())
                .author(pdDocumentInformation.getAuthor())
                .build();
    } catch (IOException e) {
      throw new PdfProcessingErrorException(e);
    }
    finally{
      try {
        document.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
