package org.ing.hackathon.totalrecall.docprocessor.model.docstore;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.time.OffsetDateTime;

@Data
@Builder
public class DocPayload<T> {

  private String text;
  private T payload;

}
