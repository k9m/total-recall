package org.ing.hackathon.totalrecall.api.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ObjectMapperConfig {

  @Bean
  ObjectMapper createObjectMapper(){
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    final SimpleModule module = new SimpleModule();

//    module.addSerializer(OffsetDateTime.class, new DateTimeJacksonSerializer());
//    module.addDeserializer(OffsetDateTime.class, new DateTimeJacksonDeserializer());

    objectMapper.registerModule(module);

    log.info("@@@ Custom Objectmapper initialised");

    return objectMapper;
  }

}
