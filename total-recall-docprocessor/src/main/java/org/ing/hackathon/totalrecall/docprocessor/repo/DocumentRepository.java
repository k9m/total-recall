package org.ing.hackathon.totalrecall.docprocessor.repo;

import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "documents", collectionResourceRel = "documents")
public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {
}
