package org.ing.hackathon.totalrecall.docprocessor.repo;

import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.Document;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.DocumentMasks;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "masks", collectionResourceRel = "masks")
@CrossOrigin(origins = "http://localhost:4200")
public interface DocumentMasksRepository extends PagingAndSortingRepository<DocumentMasks, String> {
}
