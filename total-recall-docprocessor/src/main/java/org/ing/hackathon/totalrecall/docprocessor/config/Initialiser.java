package org.ing.hackathon.totalrecall.docprocessor.config;

import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.service.pdf.PdfMetaDataService;
import org.ing.hackathon.totalrecall.docprocessor.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
    loadFilesInFolder("sample");
    loadFilesInFolder("sample-ignored");
  }

  private void loadFilesInFolder(final String folderName){
    try {
      fileUtils.listFilesOnClasspath("classpath:" + folderName + "/*.pdf").forEach(f -> insertDocument(folderName, f));
    } catch (Exception e) {
      log.warn(folderName + " folder not found!");
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
