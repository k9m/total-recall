package org.ing.hackathon.totalrecall.docprocessor.model.docprocessor;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.PageMasking;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(exclude={"masks"})
public class DocumentMasks {
  @Id
  private String id;


  @Tolerate
  public DocumentMasks(){}
}
