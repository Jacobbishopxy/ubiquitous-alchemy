/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controller;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.AdjustmentInfoUpdate;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.AdjustmentInfoService;

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
  @Operation(summary = "Get all adjustment infos by adjustment record id.")
  List<AdjustmentInfo> getAdjustmentInfosByAdjustmentRecordId(
      @RequestParam(value = "adjustment_record_id", required = true) Long adjustmentRecordId) {
    return aService.getAdjustmentInfosByAdjustmentRecordId(adjustmentRecordId);
  }

  @GetMapping("/adjustment_infos/overview")
  @Operation(summary = "Get all adjustment infos sorted by descend time.")
  List<AdjustmentInfo> getAdjustmentInfosOverview(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size) {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented yet");
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
    return aService
        .modifyAdjustmentInfo(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("AdjustmentInfo for id: %s not found", id)));
  }
}
