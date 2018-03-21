package org.ing.hackathon.totalrecall.docprocessor.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ing.hackathon.totalrecall.docprocessor.model.api.Client;
import org.ing.hackathon.totalrecall.docprocessor.model.api.Document;
import org.ing.hackathon.totalrecall.docprocessor.repo.ClientRepository;
import org.ing.hackathon.totalrecall.docprocessor.repo.DocumentRepository;
import org.ing.hackathon.totalrecall.docprocessor.util.FileUtils;
import org.ing.hackathon.totalrecall.docprocessor.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Configuration
public class Initialiser {

  @Autowired
  private JsonUtils jsonUtils;

  @Autowired
  private FileUtils fileUtils;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private DocumentRepository documentRepository;

  @PostConstruct
  public void init(){
    List<Client> clients = jsonUtils.readJSON("data/clients.json", new TypeReference<List<Client>>(){});
    clientRepository.saveAll(clients);

    final byte[] financialStatements = fileUtils.getBytesForFile("data/documents/financial-statements.pdf");
    final Document document1 = Document.builder()
            .documentId(UUID.randomUUID().toString())
            .documentType("FINANCIAL-STATEMENT")
            .fileName("financial-statements.pdf")
            .clientId(1L)
            .document(financialStatements)
            .build();

     documentRepository.save(document1);

  }
}
