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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class DocStoreEndpoint {

  @Autowired
  private final ElasticClientImpl elasticClient;

  @PostMapping(path = "save/{typeName}")
  public void saveDoc(
          @PathVariable final String typeName,
          @RequestBody final DocWrapper document){
    log.info("Save document {},{}", typeName, document.getId());
    elasticClient.saveDoc(document, typeName.toLowerCase());
  }

  @PutMapping(path = "saveAny/{typeName}/{id}")
  public void saveAny(
          @PathVariable final String id,
          @PathVariable final String typeName,
          @RequestBody final Object document){
    log.info("Save any {},{}", typeName, id);
    elasticClient.saveAny(id, typeName.toLowerCase(), document);
  }

  @GetMapping(path = "get/{typeName}/{id}")
  public DocWrapper getDoc(
          @PathVariable final String typeName,
          @PathVariable final String id){
    log.info("Get document {},{}", typeName, id);
    return elasticClient.getDoc(id, typeName.toLowerCase());
  }

  @GetMapping(path = "getAny/{typeName}/{id}")
  public Object getAny(
          @PathVariable final String typeName,
          @PathVariable final String id){
    log.info("Get document {},{}", typeName, id);
    return elasticClient.getAny(id, typeName.toLowerCase());
  }

  @PostMapping(path = "save-mask")
  public void saveMask(@RequestBody final DocumentMasking mask){
    log.info("Save mask {}", mask.getPageMasking());
    elasticClient.saveMask(mask);
  }

  @GetMapping(path = "get-masks")
  public void getMasks(@RequestBody final DocumentMasking mask){
    log.info("Save mask {}", mask.getPageMasking());
    elasticClient.saveMask(mask);
  }

  @GetMapping(path = "deleteIndex/{typeName}")
  public void deleteIndex(@PathVariable final String typeName){
    elasticClient.deleteAll(typeName);
  }

}
