package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.service.PdfToImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/docprocessor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocProcessorEndpoint {

  @Autowired
  public PdfToImageConverter pdfToImageConverter;

  @PostMapping("/process/{typeName}")
  public Object handleFileUpload(@RequestParam("file") final MultipartFile file,
                                 @PathVariable final String typeName,
                                 RedirectAttributes redirectAttributes) throws IOException {

    byte[] bytes = file.getBytes();

    //TODO process bytes with PDF parser
    //TODO use NLP tool

    return null;
  }

  @GetMapping(path = "test1", produces = "image/jpeg")
  public byte[] testImage() throws IOException {
    log.info("here");
    return pdfToImageConverter.convertToByte();
  }

  @GetMapping(path = "test2")
  public Map<String,String> testObject() throws IOException {
    log.info("here");
    Map<String,String> blah = new HashMap<>();

    blah.put("test", pdfToImageConverter.convertToImage());

    return blah;
  }

}
