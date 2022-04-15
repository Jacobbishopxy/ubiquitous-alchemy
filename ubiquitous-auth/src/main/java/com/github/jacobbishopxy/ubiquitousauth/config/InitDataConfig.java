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
  private List<String> initPrivileges;
  private List<InitRole> initRoles;
  private List<InitUser> initUsers;

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

  public static class InitUser {
    private String name;
    private String email;
    private List<String> roles;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public List<String> getRoles() {
      return roles;
    }

    public void setRoles(List<String> roles) {
      this.roles = roles;
    }
  }

  // ======================== getter and setter ========================

  public Boolean getShouldInitialize() {
    return shouldInitialize;
  }

  public void setShouldInitialize(Boolean shouldInitialize) {
    this.shouldInitialize = shouldInitialize;
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

  public void setInitUsers(List<InitUser> initUsers) {
    this.initUsers = initUsers;
  }

  public List<InitUser> getInitUsers() {
    return initUsers;
  }

}
