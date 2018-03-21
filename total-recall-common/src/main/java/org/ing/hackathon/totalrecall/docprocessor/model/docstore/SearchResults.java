package org.ing.hackathon.totalrecall.docprocessor.model.docstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class SearchResults {

  @JsonProperty("totalCount")
  private Long totalCount;

  @JsonProperty("pageCount")
  private Integer pageCount;

  @JsonProperty("pageSize")
  private Integer pageSize;

  @JsonProperty("page")
  private Integer page;

  @JsonProperty("articles")
  private List<DocWrapper> docs;

  @Tolerate
  public SearchResults(){}
}
