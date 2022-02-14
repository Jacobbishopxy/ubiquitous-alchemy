package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/promotion_record")
public class PromotionRecordController {

  @GetMapping("/index")
  public String index() {
    return "welcome";
  }
}
