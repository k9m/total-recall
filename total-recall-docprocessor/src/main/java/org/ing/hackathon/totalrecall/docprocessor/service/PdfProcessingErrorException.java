package org.ing.hackathon.totalrecall.docprocessor.service;

import java.io.IOException;

public class PdfProcessingErrorException extends RuntimeException {
  public PdfProcessingErrorException(IOException e) {
    super(e);
  }
}
