package org.ing.hackathon.totalrecall.docprocessor.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DocumentImage {

  private String documentId;
  private Integer nrPages;
  private List<byte[]> pages = new ArrayList<>();

  @Tolerate
  public DocumentImage() {}
}
