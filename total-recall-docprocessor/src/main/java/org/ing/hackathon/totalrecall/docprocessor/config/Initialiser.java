package org.ing.hackathon.totalrecall.docprocessor.config;

import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.DocumentMetaData;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.service.PdfMetaDataService;
import org.ing.hackathon.totalrecall.docprocessor.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Configuration
public class Initialiser {

  @Autowired
  private FileUtils fileUtils;

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private PdfMetaDataService pdfMetaDataService;


  @PostConstruct
  public void init(){
    final byte[] financialStatements = fileUtils.getBytesForFile("sample/financial-statements.pdf");
    final String documentId = UUID.randomUUID().toString();
    final Document document = Document.builder()
            .documentId(documentId)
            .documentType("FINANCIAL-STATEMENT")
            .fileName("financial-statements.pdf")
            .clientId(1L)
            .document(financialStatements)
            .build();

    final DocumentMetaData documentMetaData = pdfMetaDataService.getNrPages(financialStatements);
    document.setDocumentMetaData(documentMetaData);
    documentMetaData.setDocument(document);

     documentRepository.save(document);

  }
}
