package org.ing.hackathon.totalrecall.api.config.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.ing.hackathon.totalrecall.api.model.api.docstore.DocWrapper;
import org.ing.hackathon.totalrecall.api.model.api.docstore.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticClientImpl implements ElasticClient {

  @Autowired
  private final ObjectMapper objectMapper;

  @Autowired
  private final Client client;


  @Override
  public void saveDoc(final DocWrapper article, final String typeName) {
    final String articleJsonString;
    try {
      articleJsonString = objectMapper.writeValueAsString(article);
      client.prepareIndex(typeName, article.getId())
              .setType(typeName)
              .setId(article.getId())
              .setSource(articleJsonString, XContentType.JSON)
              .get();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DocWrapper getDoc(String id, final String typeName) {
    final GetResponse response = client.prepareGet(typeName, typeName, id).get();

    final DocWrapper article;
    if(response.isExists()){
      try {
        article = objectMapper.readValue(response.getSourceAsString(), DocWrapper.class);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    else{
      article = null;
    }

    return article;
  }

  @Override
  public List<DocWrapper> getDocByIds(List<String> ids, final String typeName) {
    final MultiGetRequestBuilder getBuilder = client.prepareMultiGet();
    ids.forEach( id -> getBuilder.add(typeName, typeName, id));

    final MultiGetResponse response = getBuilder.get();
    return Arrays.stream(response.getResponses()).filter(r -> r.getResponse().isExists()).map( r -> {
      try {
        return objectMapper.readValue(r.getResponse().getSourceAsString(), DocWrapper.class);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toList());
  }

  @Override
  public SearchResults search(final QueryBuilder qb, final String typeName, final int page, final int pageSize) {
    final int startIndex = (page - 1) * pageSize;
    final SearchResponse response = client.prepareSearch(typeName, typeName)
            .setQuery(qb)
            .addSort("timestamp", SortOrder.DESC)
            .setSize(pageSize)
            .setFrom(startIndex)
            .execute()
            .actionGet();

    final List<DocWrapper> docs = Arrays.stream(response.getHits().getHits()).map( r -> {
      try {
        return objectMapper.readValue(r.getSourceAsString(), DocWrapper.class);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toList());

    return SearchResults.builder()
            .docs(docs)
            .totalCount(response.getHits().totalHits)
            .pageCount(response.getHits().getHits().length)
            .page(page)
            .pageSize(pageSize)
            .build();
  }

  @Override
  public void deleteAll(final String typeName){
    client.admin().indices().prepareDelete(typeName).get();
  }

  @PreDestroy
  public void cleanUp(){
    client.close();
  }


}
