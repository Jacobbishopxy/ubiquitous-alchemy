/**
 * Created by Jacob Xie on 2/17/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.services.PromoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * PromoterController
 *
 * Since table `Promoter` is created by project (server-nodejs), we only need a
 * simple
 * GET method to get all promoters.
 */
@RestController
@RequestMapping("v1")
public class PromoterController {

  @Autowired
  private PromoterService service;

  @GetMapping("/promoters")
  List<Promoter> getPromoters() {
    return service.getPromoters();
  }
}
