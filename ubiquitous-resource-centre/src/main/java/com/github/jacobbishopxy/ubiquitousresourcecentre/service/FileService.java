/**
 * Created by Jacob Xie on 6/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousresourcecentre.config.DbConnections;
import com.github.jacobbishopxy.ubiquitousresourcecentre.domain.SimpleFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Autowired
  DbConnections conns;

  // =======================================================================
  // Query methods
  // =======================================================================

  public List<GridFSFile> checkFileList(String key, PageRequest page) {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    Query query = new Query();
    if (page != null) {
      query.with(page);
    }
    GridFSFindIterable iterable = template.find(query);
    List<GridFSFile> res = new ArrayList<>();
    iterable.forEach(res::add);

    return res;
  }

  public List<GridFSFile> checkFileList(String key, PageRequest page, String regex) {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    Query query = new Query(Criteria.where("filename").regex(regex));
    if (page != null) {
      query.with(page);
    }
    GridFSFindIterable iterable = template.find(query);
    List<GridFSFile> res = new ArrayList<>();
    iterable.forEach(res::add);

    return res;
  }

  public Optional<GridFSFile> checkFileById(String key, String id) {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    Query query = new Query(Criteria.where("_id").is(id));
    GridFSFile res = template.findOne(query);
    if (res == null) {
      return Optional.empty();
    } else {
      return Optional.of(res);
    }
  }

  public Optional<GridFSFile> checkFile(String key, String regex) {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    Query query = new Query(Criteria.where("filename").regex(regex));
    GridFSFile res = template.findOne(query);
    if (res == null) {
      return Optional.empty();
    } else {
      return Optional.of(res);
    }
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  public String addFile(String key, MultipartFile file) throws Exception {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    DBObject metadata = new BasicDBObject();
    metadata.put("fileSize", file.getSize());
    Object fileID = template.store(file.getInputStream(), file.getOriginalFilename(),
        file.getContentType(), metadata);

    return fileID.toString();
  }

  public void deleteFile(String key, String id) throws Exception {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    template.delete(new Query(Criteria.where("_id").is(id)));
  }

  public SimpleFile downloadFile(String key, String id) throws IOException {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    GridFSFile file = template.findOne(new Query(Criteria.where("_id").is(id)));
    SimpleFile res = new SimpleFile();

    if (file != null && file.getMetadata() != null) {
      res.setFilename(file.getFilename());
      res.setFileType(file.getMetadata().get("_contentType").toString());
      res.setFileSize(Integer.parseInt(file.getMetadata().get("fileSize").toString()));
      res.setFile(IOUtils.toByteArray(template.getResource(file).getInputStream()));
    }

    return res;
  }

  public SimpleFile viewImage(String key, String id) throws Exception {
    GridFsTemplate template = conns.getGridFsTemplate(key);

    GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
    SimpleFile file = new SimpleFile();

    if (gridFSFile != null && gridFSFile.getMetadata() != null) {
      file.setFilename(gridFSFile.getFilename());
      String fileType = gridFSFile.getMetadata().get("_contentType").toString();
      if (!fileType.startsWith("image")) {
        throw new Exception("File is not an image");
      }
      file.setFileType(fileType);
      file.setFileSize(Integer.parseInt(gridFSFile.getMetadata().get("fileSize").toString()));
      file.setFile(IOUtils.toByteArray(template.getResource(gridFSFile).getInputStream()));
    }

    return file;
  }
}
