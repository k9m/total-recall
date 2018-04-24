package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(exclude={"documentPages", "documentMetaData"})
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

  @OneToMany(fetch = FetchType.LAZY,
          cascade =  CascadeType.ALL,
          mappedBy = "document")
  private List<DocumentPages> documentPages;

  @Lob
  @JsonIgnore
  private byte[] document;

  @Tolerate
  public Document(){}
}
