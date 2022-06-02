/**
 * Created by Jacob Xie on 6/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.controller;

import com.github.jacobbishopxy.ubiquitousresourcecentre.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class FileController {

  @Autowired
  private FileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<?> upload(@RequestParam("key") String key, @RequestParam("file") MultipartFile file)
      throws Exception {
    String fileID = fileService.addFile(key, file);
    return ResponseEntity.ok(fileID);
  }
}
