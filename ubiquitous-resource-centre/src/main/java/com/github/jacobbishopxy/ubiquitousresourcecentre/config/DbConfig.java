/**
 * Created by Jacob Xie on 6/1/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre.config;

import com.mongodb.MongoCredential;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.github.jacobbishopxy.ubiquitousresourcecentre")
@PropertySource("classpath:persistence.properties")
public class DbConfig {

  @Autowired
  private Environment env;

  @Bean
  public MongoClientFactoryBean dataSource() {

    String database = env.getProperty("mongodb-database");
    String host = env.getProperty("mongodb-host");
    Integer port = Integer.parseInt(env.getProperty("mongodb-port"));
    String username = env.getProperty("mongodb-username");
    String password = env.getProperty("mongodb-password");

    MongoClientFactoryBean mongo = new MongoClientFactoryBean();

    MongoCredential credential = MongoCredential.createCredential(
        username,
        database,
        password.toCharArray());

    mongo.setCredential(new MongoCredential[] { credential });
    mongo.setHost(host);
    mongo.setPort(port);
    return mongo;
  }

}
