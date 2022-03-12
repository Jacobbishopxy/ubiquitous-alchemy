/**
 * Created by Jacob Xie on 2/17/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services.PromoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * PromoterController
 *
 * Since table `Promoter` is created by project (server-nodejs), we only need a
 * simple
 * GET method to get all promoters.
 */
@Tag(name = "Utility")
@RestController
@RequestMapping("v1")
public class PromoterController {

  @Autowired
  private PromoterService service;

  @Operation(description = "Get all promoters.")
  @GetMapping("/promoters")
  List<Promoter> getPromoters() {
    return service.getPromoters();
  }
}
