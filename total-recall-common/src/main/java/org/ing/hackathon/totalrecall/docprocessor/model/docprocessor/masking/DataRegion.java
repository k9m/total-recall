package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking;

import lombok.Data;

@Data
public class DataRegion {
  private boolean nlp;
  private String field;
  private int x1;
  private int y1;
  private int x2;
  private int y2;
}
