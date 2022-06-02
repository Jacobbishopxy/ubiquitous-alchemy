/**
 * Created by Jacob Xie on 6/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.service;

import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Autowired
  Map<String, GridFsTemplate> map;

  public String addFile(String key, MultipartFile file) throws Exception {

    GridFsTemplate template = map.get(key);
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
