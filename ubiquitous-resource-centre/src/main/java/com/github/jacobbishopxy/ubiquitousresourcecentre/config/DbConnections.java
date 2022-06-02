package com.github.jacobbishopxy.ubiquitousresourcecentre.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class DbConnections {

  @Bean
  public Map<String, MongoTemplate> mongoTemplateMap(DbConfig config) throws Exception {
    Map<String, MongoTemplate> map = new HashMap<>();
    for (DbConfig.Conn conn : config.getCons()) {
      map.put(conn.getKey(), config.mongoTemplate(conn, conn.getDatabase()));
    }
    return map;
  }

  @Bean
  public Map<String, GridFsTemplate> gridFsTemplateMap(DbConfig config) throws Exception {
    Map<String, GridFsTemplate> map = new HashMap<>();
    for (DbConfig.Conn conn : config.getCons()) {
      map.put(conn.getKey(), config.gridFsTemplate(conn, conn.getDatabase()));
    }
    return map;
  }
}
