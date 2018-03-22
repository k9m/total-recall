package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking;

import lombok.Data;

import java.util.List;

@Data
public class DocumentMasking {
  private String type;
  private List<PageMasking> pageMasking;
}
