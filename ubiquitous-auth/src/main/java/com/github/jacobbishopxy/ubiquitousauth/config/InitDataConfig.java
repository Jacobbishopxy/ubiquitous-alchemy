/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "initialization")
@PropertySource(value = "classpath:init.yml", factory = YamlPropertySourceFactory.class)
public class InitDataConfig {

  private Boolean shouldInitialize;
  private String supervisorName;
  private String supervisorEmail;
  private List<String> initPrivileges;
  private List<InitRole> initRoles;

  public static class InitRole {
    private String name;
    private List<String> privileges;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<String> getPrivileges() {
      return privileges;
    }

    public void setPrivileges(List<String> privileges) {
      this.privileges = privileges;
    }
  }

  // ======================== getter and setter ========================

  public Boolean getShouldInitialize() {
    return shouldInitialize;
  }

  public void setShouldInitialize(Boolean shouldInitialize) {
    this.shouldInitialize = shouldInitialize;
  }

  public String getSupervisorName() {
    return supervisorName;
  }

  public void setSupervisorName(String supervisorName) {
    this.supervisorName = supervisorName;
  }

  public String getSupervisorEmail() {
    return supervisorEmail;
  }

  public void setSupervisorEmail(String supervisorEmail) {
    this.supervisorEmail = supervisorEmail;
  }

  public List<String> getInitPrivileges() {
    return initPrivileges;
  }

  public void setInitPrivileges(List<String> initPrivileges) {
    this.initPrivileges = initPrivileges;
  }

  public List<InitRole> getInitRoles() {
    return initRoles;
  }

  public void setInitRoles(List<InitRole> initRoles) {
    this.initRoles = initRoles;
  }

}
