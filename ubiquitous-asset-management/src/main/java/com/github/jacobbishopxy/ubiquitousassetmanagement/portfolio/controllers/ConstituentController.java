/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.ConstituentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class ConstituentController {

  @Autowired
  private ConstituentService constituentService;

  @GetMapping("/constituent")
  List<Constituent> getConstituentsByAdjustmentRecordId(
      @RequestParam(value = "adjustmentRecordId", required = true) Integer adjustmentRecordId) {
    return constituentService.getConstituentsByAdjustmentRecordId(adjustmentRecordId);
  }

  @GetMapping("/constituent/{id}")
  Constituent getConstituentById(@PathVariable("id") Integer id) {
    return constituentService
        .getConstituentById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Constituent for id: %s not found", id)));
  }

  @GetMapping("/constituents")
  List<Constituent> getConstituentsByAdjustmentRecordIds(
      @RequestParam(value = "adjustmentRecordIds", required = true) List<Integer> adjustmentRecordIds) {
    return constituentService.getConstituentsByAdjustmentRecordIds(adjustmentRecordIds);
  }

  @PostMapping("/constituent")
  Constituent createConstituent(@RequestBody Constituent constituent) {
    return constituentService.createConstituent(constituent);
  }

  @PutMapping("/constituent/{id}")
  Constituent updateConstituent(
      @PathVariable("id") int id,
      @RequestBody Constituent constituent) {
    return constituentService
        .updateConstituent(id, constituent)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("Constituent for id: %s not found", id)));
  }

  @DeleteMapping("/constituent/{id}")
  void deleteConstituent(@PathVariable("id") int id) {
    constituentService.deleteConstituent(id);
  }
}
