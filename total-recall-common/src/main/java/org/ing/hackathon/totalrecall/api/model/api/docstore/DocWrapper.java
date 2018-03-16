package org.ing.hackathon.totalrecall.api.model.api.docstore;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.time.OffsetDateTime;

@Data
@Builder
public class DocWrapper<T> {

  private String id ;
  private OffsetDateTime timeStamp;
  private T document;

  @Tolerate
  public DocWrapper(){}

}
