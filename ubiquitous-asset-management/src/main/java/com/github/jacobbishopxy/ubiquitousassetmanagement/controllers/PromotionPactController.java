/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionPact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.services.PromotionPactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PromotionPact", description = "PromotionPact related operations")
@RestController
@RequestMapping("v1")
public class PromotionPactController {

  @Autowired
  private PromotionPactService service;

  @Operation(description = "Get all promotion pact.")
  @GetMapping("/promotion_pact")
  List<PromotionPact> getPromotionPacts() {
    return service.getAllPromotionPacts();
  }

  @Operation(description = "Get a promotion pact by name.")
  @GetMapping("/promotion_pact/{name}")
  PromotionPact getPromotionPact(@PathVariable("name") String name) {
    return service.getPromotionPact(name).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionPact %s not found", name)));
  }

  @Operation(description = "Create a promotion pact. Notice that `startDate` must be less than `endDate`")
  @PostMapping("/promotion_pact")
  PromotionPact createPromotionPact(@RequestBody PromotionPact promotionPact) {
    promotionPact.validate();
    return service.createPromotionPact(promotionPact);
  }

  @Operation(description = "Update a promotion pact. Notice that `startDate` must be less than `endDate`")
  @PutMapping("/promotion_pact/{name}")
  PromotionPact updatePromotionPact(
      @PathVariable("name") String name,
      @RequestBody PromotionPact promotionPact) {
    promotionPact.validate();
    return service
        .updatePromotionPact(name, promotionPact)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("PromotionPact %s not found", name)));
  }

  @Operation(description = "Delete a promotion pact.")
  @DeleteMapping("/promotion_pact/{name}")
  void deletePromotionPact(@PathVariable("name") String name) {
    service.deletePromotionPact(name);
  }
}
