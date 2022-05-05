package com.github.jacobbishopxy.ubiquitousauth.controller;

import java.net.URI;

// import com.github.jacobbishopxy.ubiquitousauth.config.CasConfig;
import com.github.jacobbishopxy.ubiquitousauth.service.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class WelcomeController {

  // @Autowired
  // CasConfig casConfig;

  @Autowired
  ValidationService validationService;

  // static final String AUTH_TOKEN = "AUTH_TOKEN";

  @GetMapping("/")
  public String index() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return "Hello, " + auth.getName() + "!";
  }

  @GetMapping("/check_logged_in")
  public String checkLoggedIn(@CookieValue(value = "SIAMTGT", required = false) String cookie) {
    if (cookie == null) {
      return null;
    }
    try {
      return validationService.validate(cookie);
    } catch (Exception e) {
      return null;
    }
  }

  // @GetMapping("/is_logged_in")
  // public Boolean isLoggedIn(@CookieValue(value = AUTH_TOKEN, required = false)
  // Integer authTime) {

  // if (authTime == null) {
  // return false;
  // }

  // if (authTime < Instant.now().getEpochSecond() - casConfig.getCasTimeout()) {
  // return false;
  // }

  // return true;
  // }

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

  // @GetMapping("/redirect_test")
  // public void redirectTest(
  // HttpServletRequest request,
  // HttpServletResponse response,
  // @RequestParam("url") String url) throws URISyntaxException, IOException {

  // response.sendRedirect(url);
  // }

  // @GetMapping("/redirect")
  // ResponseEntity<Void> redirect(
  // @RequestParam("url") String url,
  // HttpServletRequest request,
  // HttpServletResponse response) {

  // // AttributePrincipal principal = (AttributePrincipal)
  // // request.getUserPrincipal();

  // // TODO:
  // // get IDM cookies and return to frontend

  // String currentTime = Instant.now().getEpochSecond() + "";

  // Cookie cookie = new Cookie(AUTH_TOKEN, currentTime);
  // response.addCookie(cookie);

  // return ResponseEntity
  // .status(HttpStatus.FOUND)
  // .location(URI.create(url))
  // .build();
  // }

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
