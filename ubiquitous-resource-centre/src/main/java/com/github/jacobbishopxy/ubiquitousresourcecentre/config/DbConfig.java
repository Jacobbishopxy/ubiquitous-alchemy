/**
 * Created by Jacob Xie on 6/1/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.config;

import java.util.List;

import com.mongodb.MongoCredential;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
@ConfigurationProperties(prefix = "mongo")
@PropertySource(value = "classpath:persistence.yml", factory = YamlPropertySourceFactory.class)
public class DbConfig {

  private List<Conn> cons;

  public static class Conn {
    private String key;
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;
    private String auth;

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getDatabase() {
      return database;
    }

    public void setDatabase(String database) {
      this.database = database;
    }

    public String getAuth() {
      return auth;
    }

    public void setAuth(String auth) {
      this.auth = auth;
    }
  }

  MongoClientFactoryBean mongo(Conn conn) {
    MongoClientFactoryBean mongo = new MongoClientFactoryBean();

    MongoCredential credential = MongoCredential.createScramSha1Credential(
        conn.getUsername(),
        conn.getAuth(),
        conn.getPassword().toCharArray());

    mongo.setCredential(new MongoCredential[] { credential });
    mongo.setHost(conn.getHost());
    mongo.setPort(conn.getPort());
    return mongo;
  }

  MongoTemplate mongoTemplate(Conn conn, String database) throws Exception {
    return new MongoTemplate(mongo(conn).getObject(), database);
  }

  GridFsTemplate gridFsTemplate(Conn conn, String database) throws Exception {
    MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(mongo(conn).getObject(), database);
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
    MappingMongoConverter converter = new MappingMongoConverter(
        dbRefResolver,
        new MongoMappingContext());

    return new GridFsTemplate(mongoDatabaseFactory, converter);
  }

  public List<Conn> getCons() {
    return cons;
  }

  public void setCons(List<Conn> cons) {
    this.cons = cons;
  }
}
