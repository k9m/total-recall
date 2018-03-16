package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequestMapping(value = "/docprocessor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocProcessorEndpoint {

  @PostMapping("/process/{typeName}")
  public Object handleFileUpload(@RequestParam("file") final MultipartFile file,
                                 @PathVariable final String typeName,
                                 RedirectAttributes redirectAttributes) throws IOException {

    byte[] bytes = file.getBytes();

    //TODO process bytes with PDF parser
    //TODO use NLP tool

    return null;
  }

}
