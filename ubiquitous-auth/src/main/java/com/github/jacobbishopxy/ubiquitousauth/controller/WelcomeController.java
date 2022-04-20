package com.github.jacobbishopxy.ubiquitousauth.controller;

import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class WelcomeController {

  @GetMapping("/")
  public String index() throws JsonProcessingException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return "Hello, " + auth.getName() + "!";
  }

  @GetMapping("/is_logged_in")
  public Boolean isLoggedIn() throws JsonProcessingException {
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

}
