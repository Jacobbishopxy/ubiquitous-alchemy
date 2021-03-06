/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.ConstituentInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.ConstituentUpdate;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.ConstituentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class ConstituentController {

  @Autowired
  private ConstituentService constituentService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/constituents")
  @Operation(summary = "Get all constituents, either by adjustment record id or ids.")
  List<Constituent> getConstituentsByAdjustmentRecordId(
      @RequestParam(value = "adjustment_record_id", required = true) Long adjustmentRecordId,
      @RequestParam(value = "adjustment_record_id", required = true) List<Long> adjustmentRecordIds) {
    if (adjustmentRecordId != null) {
      return constituentService.getConstituentsByAdjustmentRecordId(adjustmentRecordId);
    } else if (adjustmentRecordIds != null) {
      return constituentService.getConstituentsByAdjustmentRecordIds(adjustmentRecordIds);
    } else {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "adjustment_record_id or adjustment_record_ids is required");
    }
  }

  @GetMapping("/constituent/{id}")
  @Operation(summary = "Get constituent by id.")
  Constituent getConstituentById(@PathVariable("id") Long id) {
    return constituentService
        .getConstituentById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Constituent for id: %s not found", id)));
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/constituent")
  @Operation(summary = "Create constituent.")
  Constituent createConstituent(@RequestBody ConstituentInput dto) {
    return constituentService.createConstituent(ConstituentInput.intoConstituent(dto));
  }

  @PostMapping("/constituents")
  @Operation(summary = "Create multiple constituents.")
  List<Constituent> createConstituents(@RequestBody List<ConstituentInput> dto) {
    List<Constituent> constituents = dto.stream()
        .map(ConstituentInput::intoConstituent)
        .collect(Collectors.toList());

    return constituentService.createConstituents(constituents);
  }

  @PutMapping("/constituent/{id}")
  @Operation(summary = "Update constituent.")
  Constituent updateConstituent(
      @PathVariable("id") Long id,
      @RequestBody ConstituentInput dto) {
    return constituentService
        .updateConstituent(id, ConstituentInput.intoConstituent(dto))
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Constituent for id: %s not found", id)));
  }

  @PutMapping("/constituents")
  @Operation(summary = "Update multiple constituents.")
  List<Constituent> updateConstituents(@RequestBody List<ConstituentInput> dto) {
    List<Constituent> constituents = dto
        .stream()
        .map(ConstituentInput::intoConstituent)
        .collect(Collectors.toList());

    return constituentService.updateConstituents(constituents);
  }

  @PatchMapping("/constituent/{id}")
  @Operation(summary = "Update a constituent's price and related fields.")
  Constituent modifyConstituent(
      @PathVariable("id") Long id,
      @RequestBody ConstituentUpdate dto) {
    return constituentService
        .modifyConstituent(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Constituent for id: %s not found", id)));
  }

  @DeleteMapping("/constituent/{id}")
  @Operation(summary = "Delete constituent.")
  void deleteConstituent(@PathVariable("id") Long id) {
    constituentService.deleteConstituent(id);
  }

  @DeleteMapping("/constituents")
  @Operation(summary = "Delete multiple constituents.")
  void deleteConstituents(@RequestBody List<Long> ids) {
    constituentService.deleteConstituents(ids);
  }
}
