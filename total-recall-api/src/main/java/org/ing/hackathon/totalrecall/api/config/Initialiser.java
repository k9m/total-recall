package org.ing.hackathon.totalrecall.api.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ing.hackathon.totalrecall.api.model.api.Client;
import org.ing.hackathon.totalrecall.api.repo.ClientRepository;
import org.ing.hackathon.totalrecall.api.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class Initialiser {

  @Autowired
  private JsonUtils jsonUtils;

  @Autowired
  private ClientRepository clientRepository;

  @PostConstruct
  public void init(){
    List<Client> clients = jsonUtils.readJSON("data/clients.json", new TypeReference<List<Client>>(){});
    clientRepository.saveAll(clients);
  }
}
