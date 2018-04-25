package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.DocumentMasking;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.ParsingContext;
import org.ing.hackathon.totalrecall.docprocessor.model.docstore.DocPayload;
import org.ing.hackathon.totalrecall.docprocessor.model.docstore.DocWrapper;
import org.ing.hackathon.totalrecall.docprocessor.nlp.BasicNlpClassifier;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.rest.RestClient;
import org.ing.hackathon.totalrecall.docprocessor.service.ocr.OcrService;
import org.ing.hackathon.totalrecall.docprocessor.service.pdf.PdfParser;
import org.ing.hackathon.totalrecall.docprocessor.service.pdf.PdfToImageConverter;
import org.k9m.k9nlp.model.document.DocumentProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

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

  @Autowired
  public PdfParser pdfParser;

  @Autowired
  public OcrService ocrService;

  @Autowired
  private BasicNlpClassifier nlpClassifier;

  @Autowired
  private final RestClient restClient;

  @GetMapping(path = "/document-images/{documentId}/{pageNumber}", produces = "image/jpeg")
  public byte[] getDocumentImages(
          @PathVariable final String documentId,
          @PathVariable final Integer pageNumber) throws IOException {

    final Document document = documentRepository.findById(documentId).orElse(null);
    return document.getDocumentPages().get(pageNumber).getPageImage();
  }

  @PutMapping(path = "/documents/{documentId}/pdf")
  public void saveMasks(
      @PathVariable final String documentId,
      @RequestBody final DocumentMasking documentMasking
  ) {
    final Document document = documentRepository.findById(documentId).orElse(null);
    final Map<String, DocPayload<DocumentProfile>> data = new HashMap<>();

    documentMasking.getPageMasking().forEach(pageMasking -> {
      pageMasking.getRegions().forEach(region -> {
        try {
          final String parsedText = pdfParser.parse(document.getDocument(), ParsingContext.builder()
                  .lowerLeftX((float) region.getX1())
                  .lowerLeftY((float) pageMasking.getPageHeight() - region.getY2())
                  .upperRightX((float) region.getX2())
                  .upperRightY((float) pageMasking.getPageHeight() - region.getY1())
                  .height((float) pageMasking.getPageHeight())
                  .width((float) pageMasking.getPageWidth())
                  .pageNr(pageMasking.getPageNumber() + 1)
                  .build());

          data.put(region.getField(), createPayload(documentId, parsedText, true));
        }
        catch (IOException exception) {
          data.put(region.getField(), null);
        }
      });
    });

    saveDocWrapper(documentId, documentMasking.getType(), data);
  }

  @PutMapping(path = "/documents/{documentId}/ocr")
  public void ocr(
          @PathVariable final String documentId,
          @RequestBody final DocumentMasking documentMasking
  ) {
    final Document document = documentRepository.findById(documentId).orElse(null);
    final Map<String, DocPayload<DocumentProfile>> data = new HashMap<>();

    documentMasking.getPageMasking().forEach(
        pageMasking -> pageMasking.getRegions().forEach(
            region -> {
              final String parsedText = ocrService.process(
                      document.getDocumentPages().get(pageMasking.getPageNumber()).getPageImage(),
                      pageMasking,
                      region);

              data.put(region.getField(), createPayload(documentId, parsedText, true));
            }
        )
    );

    saveDocWrapper(documentId, documentMasking.getType(), data);
  }

  private DocPayload<DocumentProfile> createPayload(final String documentId, final String parsedText, final boolean isNlp){
    final DocumentProfile docProfile = isNlp ? nlpClassifier.classify(documentId, parsedText) : null;
    return DocPayload.<DocumentProfile>builder()
                    .text(parsedText)
                    .payload(docProfile)
                    .build();
  }

  private void saveDocWrapper(final String documentId, final String documentType, final Map<String, DocPayload<DocumentProfile>> data){
    final DocWrapper<Map<String, DocPayload<DocumentProfile>>> docWrapper = DocWrapper.<Map<String, DocPayload<DocumentProfile>>>builder()
            .id(documentId)
            .timeStamp(OffsetDateTime.now())
            .document(data)
            .build();

    log.info("Saving Document");
    restClient.post("http://localhost:9801/save/" + documentType.toLowerCase(), docWrapper, Object.class);
  }

  @GetMapping(path = "/parse-page/{documentId}/{pageNumber}", produces = "text/plain")
  public String parsePage(
          @PathVariable final String documentId,
          @PathVariable final Integer pageNumber) throws IOException {

    final Document document = documentRepository.findById(documentId).orElse(null);

    final String parsedText = pdfParser.parseWholePage(document.getDocument(), pageNumber);

    return parsedText;
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
