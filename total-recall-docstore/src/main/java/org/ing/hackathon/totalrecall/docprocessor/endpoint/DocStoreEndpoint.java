package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.config.elastic.ElasticClientImpl;
import org.ing.hackathon.totalrecall.docprocessor.model.docprocessor.masking.DocumentMasking;
import org.ing.hackathon.totalrecall.docprocessor.model.docstore.DocWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "http://localhost:4200")
public class DocStoreEndpoint {

  @Autowired
  private final ElasticClientImpl elasticClient;

  @RequestMapping(value = "save/{typeName}", method = RequestMethod.POST)
  public void saveDoc(
          @PathVariable final String typeName,
          @RequestBody final DocWrapper document){
    log.info("Save document {},{}", typeName, document.getId());
    elasticClient.saveDoc(document, typeName.toLowerCase());
  }

  @RequestMapping(value = "saveAny/{typeName}/{id}", method = RequestMethod.POST)
  public void saveAny(
          @PathVariable final String id,
          @PathVariable final String typeName,
          @RequestBody final Object document){
    log.info("Save any {},{}", typeName, id);
    elasticClient.saveAny(id, typeName.toLowerCase(), document);
  }

  @RequestMapping(value = "get/{typeName}/{id}", method = RequestMethod.GET)
  public DocWrapper getDoc(
          @PathVariable final String typeName,
          @PathVariable final String id){
    log.info("Get document {},{}", typeName, id);
    return elasticClient.getDoc(id, typeName.toLowerCase());
  }

  @RequestMapping(value = "getAny/{typeName}/{id}", method = RequestMethod.GET)
  public Object getAny(
          @PathVariable final String typeName,
          @PathVariable final String id){
    log.info("Get document {},{}", typeName, id);
    return elasticClient.getAny(id, typeName.toLowerCase());
  }

  @RequestMapping(value = "save-mask", method = RequestMethod.POST)
  public void saveMask(@RequestBody final DocumentMasking mask){
    log.info("Save mask {}", mask.getPageMasking());
    elasticClient.saveMask(mask);
  }

  @RequestMapping(value = "deleteIndex/{typeName}", method = RequestMethod.GET)
  public void deleteIndex(@PathVariable final String typeName){
    elasticClient.deleteAll(typeName);
  }

}
