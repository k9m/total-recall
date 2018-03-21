package org.ing.hackathon.totalrecall.docprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PdfToImageConverter {

  public byte[] convert(final byte[] sample, final int page) throws IOException {
    final PDDocument document = PDDocument.load(sample);
    final PDFRenderer pdfRenderer = new PDFRenderer(document);
    final BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bim, "jpg", baos);
    baos.flush();
    final byte[] imageBytes = baos.toByteArray();
    baos.close();

    document.close();

    return imageBytes;
  }


//    PdfReader reader = new PdfReader(sample);
//    reader.getPageN(1);
//    PdfPage origPage = origPdf.getPageNumber();


//    Rectangle rect = origPage.getPageSize();
//    Document document = new Document(writerDoc);
//    Table wrapperTable = new Table(1);
//    Table containerTable = new Table(new float[]{0.5f,0.5f});
//    containerTable.setWidthPercent(100);
//    containerTable.addCell( "col1");
//    containerTable.addCell("col2");
//
//    PdfFormXObject pageCopy = origPage.copyAsFormXObject(writerDoc);
//    Image image = new Image(pageCopy);
//    image.setBorder(Border.NO_BORDER);
//    image.setAutoScale(true);
//    image.setHeight(rect.getHeight()-250);
//    wrapperTable.addCell(new Cell().add(containerTable).setBorder(Border.NO_BORDER));
//    wrapperTable.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
//    document.add(wrapperTable);
//    document.close();
//    readerDoc.close();

}
