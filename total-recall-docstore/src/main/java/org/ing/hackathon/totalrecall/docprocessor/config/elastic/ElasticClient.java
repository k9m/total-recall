package org.ing.hackathon.totalrecall.docprocessor.config.elastic;

import org.elasticsearch.index.query.QueryBuilder;
import org.ing.hackathon.totalrecall.docprocessor.model.api.docstore.DocWrapper;
import org.ing.hackathon.totalrecall.docprocessor.model.api.docstore.SearchResults;

import java.util.List;

public interface ElasticClient {

  void saveDoc(DocWrapper article, String typeName);

  DocWrapper getDoc(String id, String typeName);

  List<DocWrapper> getDocByIds(List<String> ids, final String typeName);

  SearchResults search(QueryBuilder qb, String typeName, int page, int pageSize);

  void deleteAll(String typeName);
}
