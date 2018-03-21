package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Builder
@Entity
@Data
public class DocumentMetaData {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("id")
  private Long id;

  private Integer nrPages;
  private String author;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "document_id", nullable = false)
  private Document document;

  @Tolerate
  public DocumentMetaData(){}

}
