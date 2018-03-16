package org.ing.hackathon.totalrecall.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Component
public class RestClient {


  private final ObjectMapper objectMapper;
  private final RestTemplate restTemplate;

  @Autowired
  public RestClient(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.restTemplate = new RestTemplate();
    restTemplate.setMessageConverters(Collections.singletonList(getHalMessageConverter()));
  }

  private HttpMessageConverter getHalMessageConverter() {
    final MappingJackson2HttpMessageConverter halConverter = new MappingJackson2HttpMessageConverter();
    halConverter.setSupportedMediaTypes(Arrays.asList(HAL_JSON, MediaType.APPLICATION_JSON));
    halConverter.setObjectMapper(objectMapper);

    return halConverter;
  }

  public <E,P> E put(final String url, final P payload, final Class<E> clazz){
    return save(url, payload, clazz, HttpMethod.PUT);
  }

  public <E,P> E put(final String url, final P payload, final ParameterizedTypeReference<E> typeReference){
    return save(url, payload, typeReference, HttpMethod.PUT);
  }

  public <E,P> E post(final String url, final P payload, final Class<E> clazz){
    return save(url, payload, clazz, HttpMethod.POST);
  }

  public <E,P> E post(final String url, final P payload, final ParameterizedTypeReference<E> typeReference){
    return save(url, payload, typeReference, HttpMethod.POST);
  }

  private <E,P> E save(final String url, final P payload, final Class<E> clazz, final HttpMethod method){
    HttpEntity<P> entity = new HttpEntity<>(payload, createHeaders());
    return restTemplate.exchange(url, method, entity, clazz).getBody();
  }

  private <E,P> E save(final String url, final P payload, final ParameterizedTypeReference<E> typeReference, final HttpMethod method){
    HttpEntity<P> entity = new HttpEntity<>(payload, createHeaders());
    return restTemplate.exchange(url, method, entity, typeReference).getBody();
  }

  public <E> E get(final String url, final Class<E> clazz){
    return get(url, new HashMap<>(), clazz);
  }

  public <E> E get(final String url, final Map<String,String> params, final Class<E> clazz){
    ResponseEntity<E> response = restTemplate.exchange(
      createBuilder(url, params).build().encode().toUri(),
      HttpMethod.GET,
      new HttpEntity<>(createHeaders()),
      clazz
    );

    return response.getBody();
  }

  public <E> E get(final String url, ParameterizedTypeReference<E> typeReference){
    return get(url, new HashMap<>(), typeReference);
  }

  public <E> E get(final String url, final Map<String,String> params, ParameterizedTypeReference<E> typeReference){
    ResponseEntity<E> responseEntity = restTemplate.exchange(
            createBuilder(url, params).build().encode().toUri(),
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            typeReference
    );

    return responseEntity.getBody();
  }

  public void delete(final String url){
    restTemplate.delete(url);
  }

  private static HttpHeaders createHeaders(){
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    return headers;
  }

  private static UriComponentsBuilder createBuilder(final String url, final Map<String,String> params){
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    for(Map.Entry<String,String> entry : params.entrySet()){
      builder = builder.queryParam(entry.getKey(), entry.getValue());
    }

    return builder;
  }



}
