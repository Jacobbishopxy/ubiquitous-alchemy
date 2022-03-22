/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.time.LocalTime;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.AdjustmentInfoUpdate;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.AdjustmentInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class AdjustmentInfoController {

  @Autowired
  private AdjustmentInfoService aService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/adjustment_infos")
  @Operation(summary = "Get all adjustment info.")
  List<AdjustmentInfo> getAdjustmentInfosByAdjustmentRecordId(
      @RequestParam(value = "adjustment_record_id", required = true) Long adjustmentRecordId) {
    return aService.getAdjustmentInfosByAdjustmentRecordId(adjustmentRecordId);
  }

  @GetMapping("/adjustment_info/{id}")
  @Operation(summary = "Get an adjustment info by id.")
  AdjustmentInfo getAdjustmentInfoById(@PathVariable("id") Long id) {
    return aService
        .getAdjustmentInfoById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("AdjustmentInfo for id: %s not found", id)));
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PatchMapping("/adjustment_info/{id}")
  @Operation(summary = "Update an adjustment info's adjustTime or description.")
  AdjustmentInfo updateAdjustmentInfo(
      @PathVariable("id") Long id,
      @RequestBody AdjustmentInfoUpdate dto) {

    LocalTime adjustTime = dto.adjustTime();
    String description = dto.description();

    return aService
        .modifyAdjustmentInfo(id, adjustTime, description)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("AdjustmentInfo for id: %s not found", id)));
  }
}
