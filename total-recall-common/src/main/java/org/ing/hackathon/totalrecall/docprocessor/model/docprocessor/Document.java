package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

  @OneToOne(fetch = FetchType.LAZY,
          cascade =  CascadeType.ALL,
          mappedBy = "document")
  private DocumentMetaData documentMetaData;

  @Lob
  @JsonIgnore
  private byte[] document;

  @Tolerate
  public Document(){}
}
