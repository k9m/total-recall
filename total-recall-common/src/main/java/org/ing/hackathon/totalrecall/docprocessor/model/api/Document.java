package org.ing.hackathon.totalrecall.docprocessor.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Builder
@Entity
public class Document {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String documentId;

  private String documentType;

  private Long clientId;

  private String fileName;

  @Lob
  private byte[] document;

  @Tolerate
  public Document(){}
}
