/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.controller;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.domain.PromotionPact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.service.PromotionPactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Promotion")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PROMOTION)
public class PromotionPactController {

  @Autowired
  private PromotionPactService service;

  @GetMapping("/pact")
  @Operation(summary = "Get all promotion pact.")
  List<PromotionPact> getPromotionPacts() {
    return service.getAllPromotionPacts();
  }

  @GetMapping("/pact/{name}")
  @Operation(summary = "Get a promotion pact by name.")
  PromotionPact getPromotionPact(@PathVariable("name") String name) {
    return service.getPromotionPact(name).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionPact %s not found", name)));
  }

  @PostMapping("/pact")
  @Operation(summary = "Create a promotion pact.", description = "Notice that `startDate` must be less than `endDate`")
  PromotionPact createPromotionPact(@RequestBody PromotionPact promotionPact) {
    promotionPact.validate();
    return service.createPromotionPact(promotionPact);
  }

  @Operation(summary = "Update a promotion pact.", description = "Notice that `startDate` must be less than `endDate`")
  @PutMapping("/pact/{name}")
  PromotionPact updatePromotionPact(
      @PathVariable("name") String name,
      @RequestBody PromotionPact promotionPact) {
    promotionPact.validate();
    return service
        .updatePromotionPact(name, promotionPact)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionPact %s not found", name)));
  }

  @Operation(summary = "Delete a promotion pact.")
  @DeleteMapping("/pact/{name}")
  void deletePromotionPact(@PathVariable("name") String name) {
    service.deletePromotionPact(name);
  }
}
