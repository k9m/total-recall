package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.PageMasking;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude={"masks"})
public class DocumentMasks {

  private String id;

  private List<PageMasking> masks;

  @Tolerate
  public DocumentMasks(){}

}
