package org.ing.hackathon.totalrecall.docprocessor.service;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.ParsingContext;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.DataRegion;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PdfParser {

  public String parse(final byte[] bytes, final ParsingContext parsingContext) throws IOException {
    final PdfReader reader = new PdfReader(bytes);
    final Rectangle pageBox = reader.getPageSize(reader.getPageN(parsingContext.getPageNr()));

    final float pdfHeight = pageBox.getHeight();
    final float pdfWidth = pageBox.getWidth();

    final float heightProportion = pdfHeight / parsingContext.getHeight();
    final float widthProportion = pdfWidth / parsingContext.getWidth();


    final Rectangle rect = new Rectangle(
            parsingContext.getLowerLeftX() * widthProportion,
            parsingContext.getLowerLeftY() * heightProportion,
            parsingContext.getUpperRightX() * widthProportion,
            parsingContext.getUpperRightY() * heightProportion);

    final RenderFilter regionFilter = new RegionTextRenderFilter(rect);

    final TextExtractionStrategy strategy = new FilteredTextRenderListener(
            new LocationTextExtractionStrategy(), regionFilter);
    final String parsedText = PdfTextExtractor.getTextFromPage(
            reader,
            parsingContext.getPageNr(),
            strategy);
    reader.close();

    log.info("@@@ Text: {}", parsedText);

    return parsedText;
  }

  public String parseWholePage(final byte[] bytes, final Integer pageNumber) throws IOException {
    final PdfReader reader = new PdfReader(bytes);
    final TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy());
    final String parsedText = PdfTextExtractor.getTextFromPage(
            reader,
            pageNumber,
            strategy);
    reader.close();

    return parsedText;
  }

  public String ocrPdf(final byte[] bytes, final int pageIndex, final DataRegion region) {
    //TODO
    return null;
  }

}
