package org.ing.hackathon.totalrecall.docprocessor.config;

import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.service.PdfMetaDataService;
import org.ing.hackathon.totalrecall.docprocessor.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
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
    try {
      fileUtils.listFilesOnClasspath("classpath:sample-ignored/*.pdf").forEach(f -> insertDocument("sample-ignored", f));
    } catch (Exception e) {
      log.warn("No ignored folder found!");
    }
  }

  private void insertDocument(final String folder, final String fileName){
    final String[] fileBits = fileName.split("_");
    final byte[] documentBytes = fileUtils.getBytesForFile(folder + "/" + fileName);
    final String documentId = fileBits[2].split("\\.")[0];

    documentRepository.save(pdfMetaDataService.createDocumentProfile(
            documentId,
            fileBits[1],
            fileBits[1],
            Long.parseLong(fileBits[0]),
            documentBytes));
  }
}
