package org.ing.hackathon.totalrecall.api.config.elastic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;


@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticInitialiser implements ApplicationListener<ApplicationReadyEvent> {

  private final TransportClient client;

  @Override
  @Retryable(
          value = { Exception.class },
          maxAttempts = 5,
          backoff = @Backoff(delay = 3000))
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    log.warn("@@@ Initialising ES Index...");
    initIndex();
    log.warn("@@@ ES Index initialised");
  }

  private void initIndex(){
    try {
      final String indexTemplate = StreamUtils.copyToString(new ClassPathResource("elastic/index-template.json").getInputStream(), Charset.forName("UTF-8"));
      final String indexName = "news";
      final IndicesExistsResponse existResponse = client.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet();

      if (existResponse.isExists()) {
        log.warn("@@@ Index [{}] already exists, skipping creation @@@", indexName);
      }
      else {
        final CreateIndexRequest request = new CreateIndexRequest("news");
        request.source(indexTemplate, XContentType.JSON);
        client.admin().indices().create(request).actionGet();

        log.warn("@@@ Index [{}] Created @@@", indexName);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}