package org.ing.hackathon.totalrecall.api.endpoint;

import lombok.RequiredArgsConstructor;
import org.ing.hackathon.totalrecall.api.config.elastic.ElasticClientImpl;
import org.ing.hackathon.totalrecall.api.model.api.docstore.DocWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/docstore")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocStoreEndpoint {

  @Autowired
  private final ElasticClientImpl elasticClient;

  @RequestMapping(value = "save/{typeName}", method = RequestMethod.POST)
  public void saveDoc(
          @PathVariable final String typeName,
          @RequestBody final Object document){
    elasticClient.saveDoc(DocWrapper.builder()
            .id(UUID.randomUUID().toString())
            .timeStamp(OffsetDateTime.now())
            .document(document)
            .build(), typeName);
  }

  @RequestMapping(value = "get/{typeName}/{id}", method = RequestMethod.GET)
  public DocWrapper saveDoc(
          @PathVariable final String typeName,
          @PathVariable final String id){
    return elasticClient.getDoc(id, typeName);
  }

  @RequestMapping(value = "deleteIndex/{typeName}", method = RequestMethod.GET)
  public void deleteIndex(@PathVariable final String typeName){
    elasticClient.deleteAll(typeName);
  }

}
