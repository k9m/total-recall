package org.ing.hackathon.totalrecall.docprocessor.model;

import lombok.Data;

import java.util.List;

@Data
public class DocumentMasking {
  private List<PageMasking> pageMasking;
}
