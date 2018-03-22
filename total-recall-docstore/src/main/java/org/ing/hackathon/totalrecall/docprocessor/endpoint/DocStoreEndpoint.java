package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import org.ing.hackathon.totalrecall.docprocessor.config.elastic.ElasticClientImpl;
import org.ing.hackathon.totalrecall.docprocessor.model.docstore.DocWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocStoreEndpoint {

  @Autowired
  private final ElasticClientImpl elasticClient;

  @RequestMapping(value = "save/{typeName}", method = RequestMethod.POST)
  public void saveDoc(
          @PathVariable final String typeName,
          @RequestBody final DocWrapper document){
    elasticClient.saveDoc(document, typeName);
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
