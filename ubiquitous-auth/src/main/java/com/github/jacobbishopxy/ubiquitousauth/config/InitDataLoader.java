/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:constants.properties")

public class InitDataLoader {

  @Value("${supervisorName}")
  private String supervisorName;

  @Value("${supervisorEmail}")
  private String supervisorEmail;

  // TODO:
  // user service

}
