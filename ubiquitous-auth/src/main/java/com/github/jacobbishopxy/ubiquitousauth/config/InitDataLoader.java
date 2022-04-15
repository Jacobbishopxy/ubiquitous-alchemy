/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.config;

import java.util.List;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserRole;
import com.github.jacobbishopxy.ubiquitousauth.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class InitDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  Logger logger = LogManager.getLogger(InitDataLoader.class);

  @Autowired
  private InitDataConfig initDataConfig;

  @Autowired
  private RegistrationService registrationService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void onApplicationEvent(final ContextRefreshedEvent event) {

    logger.info("Initializing data... " + initDataConfig.getShouldInitialize());

    if (initDataConfig.getShouldInitialize() == null) {
      return;
    }
    if (!initDataConfig.getShouldInitialize()) {
      return;
    }

    // register initial privileges from config
    initDataConfig
        .getInitPrivileges()
        .stream()
        .forEach(privilegeName -> {
          registrationService.registerPrivilegeIfNotFound(privilegeName);
        });

    // register initial roles from config
    initDataConfig
        .getInitRoles()
        .stream()
        .forEach(ur -> {
          // if privilege not found, create it
          List<UserPrivilege> p = ur
              .getPrivileges()
              .stream()
              .map(privilegeName -> registrationService.registerPrivilegeIfNotFound(privilegeName))
              .collect(Collectors.toList());
          registrationService.registerRoleIfNotFound(ur.getName(), p);
        });

    // register initial users from config
    initDataConfig
        .getInitUsers()
        .stream()
        .forEach(ua -> {
          // if role not found, skip it (since role is related to privileges, creating
          // empty role is unnecessary)
          List<UserRole> r = ua
              .getRoles()
              .stream()
              .map(roleName -> registrationService.getRoleByName(roleName))
              .filter(roleOpt -> roleOpt.isPresent())
              .map(role -> role.get())
              .collect(Collectors.toList());

          registrationService.registerUserIfNotFound(ua.getName(), ua.getEmail(), true, r);
        });

    initDataConfig.setShouldInitialize(false);

    logger.info("Initialization finished");

  }

}
