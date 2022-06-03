/**
 * Created by Jacob Xie on 6/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.controller;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousresourcecentre.domain.SimpleFile;
import com.github.jacobbishopxy.ubiquitousresourcecentre.service.FileService;
import com.mongodb.client.gridfs.model.GridFSFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "File")
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class FileController {

  @Autowired
  private FileService fileService;

  // =======================================================================
  // Query methods
  // =======================================================================

  // http://localhost:8080/api/files?key=dev&page=0&size=10
  @GetMapping("/files")
  @Operation(summary = "Get file information list")
  public List<GridFSFile> getFilesInfo(@RequestParam("key") String key, @RequestParam("page") Integer page,
      @RequestParam("size") Integer size, @RequestParam(value = "regex", required = false) String regex) {
    if (page == null || size == null) {
      throw new IllegalArgumentException("page and size must be provided");
    }
    if (regex != null) {
      return fileService.checkFileList(key, PageRequest.of(page, size), regex);
    } else {
      return fileService.checkFileList(key, PageRequest.of(page, size));
    }
  }

  @GetMapping("/file/{id}")
  @Operation(summary = "Get file information by id")
  public GridFSFile getFileInfoById(@RequestParam("key") String key, @PathVariable("id") String id) {
    return fileService.checkFileById(key, id).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "File not found"));
  }

  @GetMapping("/file")
  @Operation(summary = "Get file information by regex")
  public GridFSFile getFileInfo(@RequestParam("key") String key, @RequestParam("regex") String regex) {
    return fileService.checkFile(key, regex).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "File not found"));
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  // http://localhost:8080/api/upload
  // form-data: key=dev&file=file
  @PostMapping("/upload")
  @Operation(summary = "Upload file to specified database by `key` value")
  public ResponseEntity<?> upload(@RequestParam("key") String key, @RequestParam("file") MultipartFile file)
      throws Exception {
    String fileID = fileService.addFile(key, file);
    return ResponseEntity.ok(fileID);
  }

  // http://localhost:8080/api/delete/6298affa01fe3429184175ba?key=dev
  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete file by id in specified database by `key` value")
  public void delete(@RequestParam("key") String key, @PathVariable("id") String id) throws Exception {
    fileService.deleteFile(key, id);
  }

  // http://localhost:8080/api/download/6298affa01fe3429184175ba?key=dev
  @GetMapping("/download/{id}")
  @Operation(summary = "Download file by id from specified database by `key` value")
  public ResponseEntity<ByteArrayResource> download(@RequestParam("key") String key, @PathVariable("id") String id)
      throws Exception {
    SimpleFile file = fileService.downloadFile(key, id);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(file.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"")
        .body(new ByteArrayResource(file.getFile()));
  }

  // http://localhost:8080/api/image/6298affa01fe3429184175ba?key=dev
  @GetMapping(value = "/image/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
      MediaType.IMAGE_GIF_VALUE })
  @Operation(summary = "Download image by id from specified database by `key` value")
  public @ResponseBody byte[] image(@RequestParam("key") String key, @PathVariable String id) throws Exception {
    return fileService.viewImage(key, id).getFile();
  }
}
