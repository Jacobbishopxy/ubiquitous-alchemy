/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.ConstituentInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.ConstituentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
  List<Constituent> getConstituentsByAdjustmentRecordId(
      @RequestParam(value = "adjustment_record_id", required = true) Integer adjustmentRecordId,
      @RequestParam(value = "adjustment_record_id", required = true) List<Integer> adjustmentRecordIds) {
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
  Constituent getConstituentById(@PathVariable("id") Integer id) {
    return constituentService
        .getConstituentById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Constituent for id: %s not found", id)));
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/constituent")
  Constituent createConstituent(@RequestBody ConstituentInput dto) {
    return constituentService.createConstituent(ConstituentInput.intoConstituent(dto));
  }

  @PostMapping("/constituents")
  List<Constituent> createConstituents(@RequestBody List<ConstituentInput> dto) {
    List<Constituent> constituents = dto.stream()
        .map(ConstituentInput::intoConstituent)
        .collect(Collectors.toList());

    return constituentService.createConstituents(constituents);
  }

  @PutMapping("/constituent/{id}")
  Constituent updateConstituent(
      @PathVariable("id") int id,
      @RequestBody ConstituentInput dto) {
    return constituentService
        .updateConstituent(id, ConstituentInput.intoConstituent(dto))
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Constituent for id: %s not found", id)));
  }

  @PutMapping("/constituents")
  List<Constituent> updateConstituents(@RequestBody List<ConstituentInput> dto) {
    List<Constituent> constituents = dto
        .stream()
        .map(ConstituentInput::intoConstituent)
        .collect(Collectors.toList());

    return constituentService.updateConstituents(constituents);
  }

  @DeleteMapping("/constituent/{id}")
  void deleteConstituent(@PathVariable("id") int id) {
    constituentService.deleteConstituent(id);
  }
}
