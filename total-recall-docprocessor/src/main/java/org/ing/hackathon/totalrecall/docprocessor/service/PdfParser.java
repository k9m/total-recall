package org.ing.hackathon.totalrecall.docprocessor.service;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.ParsingContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PdfParser {

  public String parse(final byte[] bytes, final ParsingContext parsingContext) throws IOException {
    final PdfReader reader = new PdfReader(bytes);
    final Rectangle rect = new Rectangle(
            parsingContext.getLowerLeftX(),
            parsingContext.getLowerLeftY(),
            parsingContext.getUpperRightX(),
            parsingContext.getUpperRightY());

    final RenderFilter regionFilter = new RegionTextRenderFilter(rect);

    final TextExtractionStrategy strategy = new FilteredTextRenderListener(
            new LocationTextExtractionStrategy(), regionFilter);
    final String parsedText = PdfTextExtractor.getTextFromPage(
            reader,
            parsingContext.getPageNr(),
            strategy);
    reader.close();

    return parsedText;
  }
}
