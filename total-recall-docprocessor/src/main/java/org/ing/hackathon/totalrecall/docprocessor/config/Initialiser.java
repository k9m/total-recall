package org.ing.hackathon.totalrecall.docprocessor.config;

import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.DocumentMetaData;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.service.PdfMetaDataService;
import org.ing.hackathon.totalrecall.docprocessor.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
    fileUtils.listFilesOnClasspath("classpath:sample/*.pdf").forEach(f -> insertDocument("sample", f));
    fileUtils.listFilesOnClasspath("classpath:sample-ignored/*.pdf").forEach(f -> insertDocument("sample-ignored", f));
  }

  private void insertDocument(final String folder, final String fileName){
    final String[] fileBits = fileName.split("_");
    final byte[] financialStatements = fileUtils.getBytesForFile(folder + "/" + fileName);
    final String documentId = fileBits[2].split("\\.")[0];
    final Document document = Document.builder()
            .documentId(documentId)
            .documentType(fileBits[1])
            .fileName(fileName)
            .clientId(Long.parseLong(fileBits[0]))
            .document(financialStatements)
            .build();

    final DocumentMetaData documentMetaData = pdfMetaDataService.getNrPages(financialStatements);
    document.setDocumentMetaData(documentMetaData);
    documentMetaData.setDocument(document);

    documentRepository.save(document);
  }
}
