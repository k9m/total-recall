package org.ing.hackathon.totalrecall.docprocessor.config.elastic;

import org.elasticsearch.index.query.QueryBuilder;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.DocumentMasking;
import org.ing.hackathon.totalrecall.docprocessor.model.docstore.DocWrapper;
import org.ing.hackathon.totalrecall.docprocessor.model.docstore.SearchResults;

import java.util.List;

public interface ElasticClient {

  void saveDoc(DocWrapper article, String typeName);

  void saveMask(DocumentMasking article);

  DocWrapper getDoc(String id, String typeName);

  List<DocWrapper> getDocByIds(List<String> ids, final String typeName);

  SearchResults search(QueryBuilder qb, String typeName, int page, int pageSize);

  void deleteAll(String typeName);
}
