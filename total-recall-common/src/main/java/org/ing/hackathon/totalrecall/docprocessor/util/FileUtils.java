package org.ing.hackathon.totalrecall.docprocessor.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class FileUtils {

  @Autowired
  private ResourcePatternResolver patternResolver;


  public byte[] getBytesForFile(final String filePath){
    try {
      return StreamUtils.copyToByteArray(new ClassPathResource(filePath).getInputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<String> listFilesOnClasspath(final String path){
    final Resource[] resources;
    try {
      resources = patternResolver.getResources(path);
      return Arrays.stream(resources).map(r -> {
        try {
          return r.getFile().getName();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }).collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
