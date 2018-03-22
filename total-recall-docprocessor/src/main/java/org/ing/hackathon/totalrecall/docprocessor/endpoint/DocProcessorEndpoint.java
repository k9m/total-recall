package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.ParsingContext;
import org.ing.hackathon.totalrecall.docprocessor.nlp.BasicNlpClassifier;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.service.PdfParser;
import org.ing.hackathon.totalrecall.docprocessor.service.PdfToImageConverter;
import org.k9m.k9nlp.model.document.DocumentProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/")
public class DocProcessorEndpoint {

  @Autowired
  public DocumentRepository documentRepository;

  @Autowired
  public PdfToImageConverter pdfToImageConverter;

  @Autowired
  public PdfParser pdfParser;

  @Autowired
  private BasicNlpClassifier nlpClassifier;

  @GetMapping(path = "/document-images/{documentId}/{pageNumber}", produces = "image/jpeg")
  public byte[] getDocumentImages(
          @PathVariable final String documentId,
          @PathVariable final Integer pageNumber) throws IOException {

    final Document document = documentRepository.findById(documentId).orElse(null);
    return pdfToImageConverter.convert(document.getDocument(), pageNumber);
  }

  @GetMapping(path = "/parse-document/{documentId}/{pageNumber}", produces = "text/plain")
  public String parseDocument(
          @PathVariable final String documentId,
          @PathVariable final Integer pageNumber) throws IOException {

    final Document document = documentRepository.findById(documentId).orElse(null);

    final String parsedText = pdfParser.parse(document.getDocument(), ParsingContext.builder()
            .lowerLeftX(36f)
            .lowerLeftY(750f)
            .upperRightX(559f)
            .upperRightY(806f)
            .pageNr(pageNumber)
            .build());

    return parsedText;
  }

  @GetMapping(path = "/parse-page/{documentId}/{pageNumber}", produces = "text/plain")
  public String parsePage(
          @PathVariable final String documentId,
          @PathVariable final Integer pageNumber) throws IOException {

    final Document document = documentRepository.findById(documentId).orElse(null);

    final String parsedText = pdfParser.parseWholePage(document.getDocument(), pageNumber);

    return parsedText.replace("[^A-Za-z0-9]", "");
  }

  @GetMapping(path = "/extract-entities/{documentId}/{pageNumber}")
  public DocumentProfile parseEntities(
          @PathVariable final String documentId,
          @PathVariable final Integer pageNumber) throws IOException {

    final Document document = documentRepository.findById(documentId).orElse(null);

    final String parsedText = pdfParser.parseWholePage(document.getDocument(), pageNumber);

    return nlpClassifier.classify(documentId, parsedText);

  }

}
