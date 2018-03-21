package org.ing.hackathon.totalrecall.docprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentProcessorService {

  @Autowired
  private final RestClient restClient;


}
