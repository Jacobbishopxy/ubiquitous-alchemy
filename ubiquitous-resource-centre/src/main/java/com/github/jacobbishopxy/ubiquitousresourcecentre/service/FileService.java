/**
 * Created by Jacob Xie on 6/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.service;

import com.github.jacobbishopxy.ubiquitousresourcecentre.config.DbConfig;
import com.github.jacobbishopxy.ubiquitousresourcecentre.config.DbConnections;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Autowired
  DbConfig config;

  @Autowired
  DbConnections conns;

  public String addFile(String key, MultipartFile file) throws Exception {

    GridFsTemplate template = conns.gridFsTemplateMap(config).get(key);
    if (template == null) {
      throw new Exception("No such key");
    }

    DBObject metadata = new BasicDBObject();

    metadata.put("fileSize", file.getSize());
    Object fileID = template.store(file.getInputStream(), file.getOriginalFilename(),
        file.getContentType(), metadata);

    return fileID.toString();
  }

}
