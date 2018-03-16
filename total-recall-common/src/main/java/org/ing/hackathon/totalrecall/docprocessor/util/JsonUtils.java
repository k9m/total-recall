package org.ing.hackathon.totalrecall.docprocessor.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;


@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class JsonUtils {

  @Autowired
  private final ObjectMapper objectMapper;

  public <T> T readJSON(final String filePath, Class<T> clazz) {
    try {
      final String jsonString = StreamUtils.copyToString(new ClassPathResource(filePath).getInputStream(), Charset.forName("UTF-8"));
      return (T)objectMapper.readValue(jsonString, clazz.getClass());

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T readJSON(final String filePath, final TypeReference<T> typeRef) {
    try {
      final String jsonString = StreamUtils.copyToString(new ClassPathResource(filePath).getInputStream(), Charset.forName("UTF-8"));
      return (T)objectMapper.readValue(jsonString, typeRef);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public String readJSONAsString(final String filePath) {
    try {
      return StreamUtils.copyToString(new ClassPathResource(filePath).getInputStream(), Charset.forName("UTF-8"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
