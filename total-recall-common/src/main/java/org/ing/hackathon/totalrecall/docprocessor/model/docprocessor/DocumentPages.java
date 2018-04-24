package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@Entity
public class DocumentPages {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private Integer pageNr;

  @Lob
  @JsonIgnore
  private byte[] pageImage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "document_id", nullable = false)
  private Document document;

  @Tolerate
  public DocumentPages(){}
}
