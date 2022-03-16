/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.time.LocalTime;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.AdjustmentInfoPatch;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.AdjustmentInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class AdjustmentInfoController {

  @Autowired
  private AdjustmentInfoService aService;

  @GetMapping("/adjustment_info")
  List<AdjustmentInfo> getAdjustmentInfosByAdjustmentRecordId(
      @RequestParam(value = "adjustmentRecordId", required = true) Integer adjustmentRecordId) {
    return aService.getAdjustmentInfosByAdjustmentRecordId(adjustmentRecordId);
  }

  @GetMapping("/adjustment_info/{id}")
  AdjustmentInfo getAdjustmentInfoById(@PathVariable("id") Integer id) {
    return aService
        .getAdjustmentInfoById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("AdjustmentInfo for id: %s not found", id)));
  }

  @PatchMapping("/adjustment_info/{id}")
  AdjustmentInfo updateAdjustmentInfo(
      @PathVariable("id") int id,
      @RequestBody AdjustmentInfoPatch dto) {

    LocalTime adjustTime = dto.adjustTime();
    String description = dto.description();

    return aService
        .modifyAdjustmentInfo(id, adjustTime, description)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("AdjustmentInfo for id: %s not found", id)));
  }
}
