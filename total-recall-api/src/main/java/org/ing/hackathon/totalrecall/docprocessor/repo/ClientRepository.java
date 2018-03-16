package org.ing.hackathon.totalrecall.docprocessor.repo;

import org.ing.hackathon.totalrecall.docprocessor.model.api.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "clients", collectionResourceRel = "clients")
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
}
