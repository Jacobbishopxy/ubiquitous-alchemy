package com.github.jacobbishopxy.ubiquitousauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.jasig.cas.client.authentication.AttributePrincipal;
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

  @RequestMapping("/hello")
  public void hello(HttpServletRequest request, HttpServletResponse response, @RequestParam("url") String url)
      throws IOException {
    AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();

    System.out.println(">>>>>>>>>>>>>>>>>> " + principal.getName());

    response.sendRedirect(url);
  }

}
