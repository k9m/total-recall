package org.ing.hackathon.totalrecall.docprocessor.service.ocr;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.DataRegion;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.PageMasking;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
@Slf4j
public class OcrService {

  public String process(
          final byte[] imageInBytes,
          final PageMasking pageMasking,
          final DataRegion region){
    final ITesseract instance = new Tesseract();
    // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
    final File tessDataFolder = LoadLibs.extractTessResources("tessdata");
    instance.setDatapath(tessDataFolder.getParent());

    try {
      final BufferedImage img = convertToBufferedImage(imageInBytes);
      return instance.doOCR(
              img,
              generateRectangle(pageMasking, region, img));
    }
    catch (TesseractException e) {
      log.error("Error while OCR-ing with Tesseract", e);
      throw new RuntimeException(e);
    }
  }

  private Rectangle generateRectangle(
          final PageMasking pageMasking,
          final DataRegion region,
          final BufferedImage img){

    final float widthProportion =  (float)img.getWidth() / (float)pageMasking.getPageWidth();
    final float heightProportion = (float)img.getHeight() / (float)pageMasking.getPageHeight();

    log.info("Page dimensions: [{},{}]", pageMasking.getPageWidth(), pageMasking.getPageHeight());
    log.info("Page proportions: [{},{}]", widthProportion, heightProportion);
    log.info("Coords=>[{},{}] -> [{},{}]",
            region.getX1(),
            region.getY1(),
            region.getX2(),
            region.getY2());

    final int x1 = (int)(region.getX1() * widthProportion);
    final int y1 = (int)(region.getY1() * heightProportion);
    final int width = (int)((region.getX2() - region.getX1()) * widthProportion);
    final int height = (int)((region.getY2() - region.getY1()) * heightProportion);

    log.info("Rect  =>[{},{}] -> [{},{}]",
            x1,
            y1,
            width,
            height);

    return new Rectangle(x1, y1, width, height);
  }

  private static BufferedImage convertToBufferedImage(final byte[] imageInBytes) {
    try {
      final InputStream in = new ByteArrayInputStream(imageInBytes);
      final BufferedImage bimg = ImageIO.read(in);

      log.info("BufferedImage dimensions: [{},{}]", bimg.getWidth(), bimg.getHeight());

      in.close();

      return bimg;
    }
    catch (IOException e) {
      log.error("Error while creating BufferedImage", e);
      throw new RuntimeException(e);
    }
  }
}
