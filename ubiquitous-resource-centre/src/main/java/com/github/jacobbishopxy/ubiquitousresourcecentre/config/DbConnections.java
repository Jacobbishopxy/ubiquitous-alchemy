package com.github.jacobbishopxy.ubiquitousresourcecentre.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class DbConnections {

  @Autowired
  DbConfig config;

  @Bean
  Map<String, MongoTemplate> mongoTemplateMap() throws Exception {
    Map<String, MongoTemplate> map = new HashMap<>();
    for (DbConfig.Conn conn : config.getCons()) {
      map.put(conn.getKey(), config.mongoTemplate(conn));
    }
    return map;
  }

  @Bean
  Map<String, GridFsTemplate> gridFsTemplateMap() throws Exception {
    Map<String, GridFsTemplate> map = new HashMap<>();
    for (DbConfig.Conn conn : config.getCons()) {
      map.put(conn.getKey(), config.gridFsTemplate(conn));
    }
    return map;
  }

  public MongoTemplate getMongoTemplate(String key) {
    try {
      return mongoTemplateMap().get(key);
    } catch (Exception e) {
      throw new RuntimeException(String.format("No such key: %s", key));
    }
  }

  public GridFsTemplate getGridFsTemplate(String key) {
    try {
      return gridFsTemplateMap().get(key);
    } catch (Exception e) {
      throw new RuntimeException(String.format("No such key: %s", key));
    }
  }
}
