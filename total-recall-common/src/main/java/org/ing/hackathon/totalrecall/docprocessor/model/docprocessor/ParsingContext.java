package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class ParsingContext {

  private Float lowerLeftX;
  private Float lowerLeftY;

  private Float upperRightX;
  private Float upperRightY;

  private Integer pageNr;

  @Tolerate
  public ParsingContext() {}
}
