/**
 * Created by Jacob Xie on 4/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousauth.Constants;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;
import com.github.jacobbishopxy.ubiquitousauth.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_INFORMATION)
public class InformationController {

  @Autowired
  private RegistrationService registrationService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/info")
  public UserAccount getUserInfo() {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    return registrationService
        .getUserByUsername(username)
        .orElse(new UserAccount(
            "visitor",
            "Please ask administrator to register an authorized account!",
            true,
            List.of()));
  }

  @GetMapping("/time")
  public String getServerTime() {
    return String.valueOf(System.currentTimeMillis());
  }

  @GetMapping("/formatted_time")
  public String getServerFormattedTime() {
    return LocalDateTime.now().format(Constants.DTF);
  }

}
