package org.ing.hackathon.totalrecall.docprocessor.model;

import lombok.Data;

import java.util.List;

@Data
public class PageMasking {
  private int pageNumber;
  private int pageWidth;
  private int pageHeight;
  private List<DataRegion> regions;
}
