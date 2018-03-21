package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import org.ing.hackathon.totalrecall.docprocessor.model.api.Document;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiEndpoint {

  @Autowired
  private final DocumentRepository documentRepository;

  @GetMapping(value = "document-list")
  public Iterable<Document> getDocuments(){
    Iterable<Document> documents = documentRepository.findAll();
    documents.forEach(d -> d.setDocument(null));

    return documents;
  }

}
