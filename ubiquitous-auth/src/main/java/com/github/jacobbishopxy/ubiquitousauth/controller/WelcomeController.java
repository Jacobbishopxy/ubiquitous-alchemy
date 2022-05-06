package com.github.jacobbishopxy.ubiquitousauth.controller;

import java.net.URI;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;
import com.github.jacobbishopxy.ubiquitousauth.dto.FlattenedUserAccount;
import com.github.jacobbishopxy.ubiquitousauth.service.RegistrationService;
// import com.github.jacobbishopxy.ubiquitousauth.config.CasConfig;
import com.github.jacobbishopxy.ubiquitousauth.service.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class WelcomeController {

  @Autowired
  private ValidationService validationService;

  @Autowired
  private RegistrationService registrationService;

  @GetMapping("/")
  public String index() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return "Hello, " + auth.getName() + "!";
  }

  @GetMapping("/check_logged_in")
  public Boolean checkLoggedIn(@CookieValue(value = "SIAMTGT", required = false) String cookie) {
    if (cookie == null) {
      return false;
    }
    try {
      String validation = validationService.validate(cookie);
      return validation != null;
    } catch (Exception e) {
      return false;
    }
  }

  @GetMapping("/is_logged_in")
  public Boolean isLoggedIn() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return auth.getName() != "anonymousUser";
  }

  @GetMapping("/redirect")
  ResponseEntity<Void> redirect(@RequestParam("url") String url) {
    return ResponseEntity
        .status(HttpStatus.FOUND)
        .location(URI.create(url))
        .build();
  }

  @GetMapping("/user")
  public FlattenedUserAccount getUser(@CookieValue(value = "SIAMTGT", required = false) String cookie) {
    if (cookie == null) {
      return null;
    }
    try {
      String username = validationService.getUsername(cookie);

      UserAccount ua = registrationService.getUserByUsername(username).orElse(new UserAccount(
          "visitor",
          "Please ask administrator to register an authorized account!",
          true,
          List.of()));

      return FlattenedUserAccount.fromUserAccount(ua);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

}
