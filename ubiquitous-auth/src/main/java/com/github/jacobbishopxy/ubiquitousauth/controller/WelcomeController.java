package com.github.jacobbishopxy.ubiquitousauth.controller;

import java.net.URI;
import java.time.Instant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.jacobbishopxy.ubiquitousauth.config.CasConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class WelcomeController {

  @Autowired
  CasConfig casConfig;

  static final String AUTH_TOKEN = "AUTH_TOKEN";

  @GetMapping("/")
  public String index() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return "Hello, " + auth.getName() + "!";
  }

  @GetMapping("/check_logged_in")
  public String checkLoggedIn(HttpServletRequest request) {

    return request.getUserPrincipal().getName();
  }

  @GetMapping("/is_logged_in")
  public Boolean isLoggedIn(@CookieValue(value = AUTH_TOKEN, required = false) Integer authTime) {

    if (authTime == null) {
      return false;
    }

    if (authTime < Instant.now().getEpochSecond() - casConfig.getCasTimeout()) {
      return false;
    }

    return true;
  }

  @GetMapping("/redirect")
  ResponseEntity<Void> redirect(
      @RequestParam("url") String url,
      HttpServletRequest request,
      HttpServletResponse response) {

    // AttributePrincipal principal = (AttributePrincipal)
    // request.getUserPrincipal();

    // TODO:
    // get IDM cookies and return to frontend

    String currentTime = Instant.now().getEpochSecond() + "";

    Cookie cookie = new Cookie(AUTH_TOKEN, currentTime);
    response.addCookie(cookie);

    return ResponseEntity
        .status(HttpStatus.FOUND)
        .location(URI.create(url))
        .build();
  }

  // @GetMapping("/logout")
  // String logout(
  // @RequestParam("url") String url,
  // HttpSession session,
  // HttpServletRequest request,
  // HttpServletResponse response) throws IOException {

  // session.removeAttribute(AUTH_TIME);

  // String logoutUrl = casConfig.getCasLogoutUrl() + "?service=" + url;

  // response.sendRedirect(logoutUrl);

  // return url;
  // }

}
