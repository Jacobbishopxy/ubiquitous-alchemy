/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.config;

import com.github.jacobbishopxy.ubiquitousauth.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public class InitDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private InitDataConfig initDataConfig;

  @Autowired
  private RegistrationService registrationService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void onApplicationEvent(final ContextRefreshedEvent event) {

    if (initDataConfig.getShouldInitialize() == null) {
      return;
    }
    if (!initDataConfig.getShouldInitialize()) {
      return;
    }

    // TODO:
    // initializing

  }

}
