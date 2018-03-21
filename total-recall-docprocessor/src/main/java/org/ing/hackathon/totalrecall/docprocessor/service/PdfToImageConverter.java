package org.ing.hackathon.totalrecall.docprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.ing.hackathon.totalrecall.docprocessor.model.DocumentImage;
import org.ing.hackathon.totalrecall.docprocessor.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PdfToImageConverter {

  private final FileUtils fileUtils;


  public byte[] convertToByte() throws IOException {
    final byte[] sample = fileUtils.getBytesForFile("sample/financial-statements.pdf");

    PDDocument document = PDDocument.load(sample);
    PDFRenderer pdfRenderer = new PDFRenderer(document);
    byte[] imageString = null;
    for (int page = 0; page < document.getNumberOfPages(); ++page) {
      BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bim, "jpg", baos);
      baos.flush();

      imageString = baos.toByteArray();
      baos.close();

      //TODO
      break;
    }

    document.close();

    return imageString;


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



  public DocumentImage convertToImage() throws IOException {
    final byte[] sample = fileUtils.getBytesForFile("sample/financial-statements.pdf");

    final PDDocument document = PDDocument.load(sample);
    final PDFRenderer pdfRenderer = new PDFRenderer(document);

    final DocumentImage documentImage = new DocumentImage();
    documentImage.setDocumentId(UUID.randomUUID().toString());
    documentImage.setNrPages(document.getNumberOfPages());

    for (int page = 0; page < document.getNumberOfPages(); ++page) {
      BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bim, "jpg", baos);
      baos.flush();
      documentImage.getPages().add(Base64.getEncoder().encode(baos.toByteArray()));
      baos.close();

    }

    document.close();

    return documentImage;
  }
}
