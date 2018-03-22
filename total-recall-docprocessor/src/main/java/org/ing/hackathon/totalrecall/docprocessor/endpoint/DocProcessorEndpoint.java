package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.service.PdfToImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class DocProcessorEndpoint {

  @Autowired
  public DocumentRepository documentRepository;

  @Autowired
  public PdfToImageConverter pdfToImageConverter;

  @GetMapping(path = "/document-images/{documentId}/{pageNumber}", produces = "image/jpeg")
  public byte[] getDocumentImages(
          @PathVariable final String documentId,
          @PathVariable final Integer pageNumber) throws IOException {

    final Document document = documentRepository.findById(documentId).orElse(null);
    return pdfToImageConverter.convert(document.getDocument(), pageNumber);
  }

}
