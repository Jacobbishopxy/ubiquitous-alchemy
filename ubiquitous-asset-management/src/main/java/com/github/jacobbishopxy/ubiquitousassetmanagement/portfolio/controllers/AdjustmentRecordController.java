/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.AdjustmentRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class AdjustmentRecordController {

  @Autowired
  private AdjustmentRecordService arService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/adjustment_records")
  @Operation(summary = "Get all adjustment records by pact id with pagination.")
  List<AdjustmentRecord> getAdjustmentRecords(
      @RequestParam(value = "pactId", required = true) Long pactId,
      @RequestParam("page") Integer page,
      @RequestParam("size") Integer size) {

    PageRequest pr = null;
    if (page != null && size != null) {
      pr = PageRequest.of(page, size);
    }

    return arService.getARSortDesc(pactId, pr);
  }

  @GetMapping("/adjustment_record/item/{id}")
  @Operation(summary = "Get adjustment record by id.")
  AdjustmentRecord getAdjustmentRecord(@PathVariable("id") Long id) {
    return arService
        .getARById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("AdjustmentRecord for id: %s not found", id)));
  }

  @GetMapping("/adjustment_record/unsettled")
  @Operation(summary = "Get unsettled adjustment record by pact id.")
  AdjustmentRecord getUnsettledAdjustmentRecord(
      @RequestParam(value = "pactId", required = true) Long pactId) {
    return arService
        .getUnsettledAR(pactId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("Unsettled adjustment record for pact id: %s not found", pactId)));
  }

  @GetMapping("/adjustment_records/unsettled")
  @Operation(summary = "Get unsettled adjustment records by pact ids.")
  List<AdjustmentRecord> getUnsettledAdjustmentRecords(
      @RequestParam(value = "pactIds", required = true) List<Long> pactIds) {
    return arService.getUnsettledARs(pactIds);
  }

  @GetMapping("/adjustment_records/latest_date")
  @Operation(summary = "Get the latest date of adjustment records by pact id.")
  List<AdjustmentRecord> getLatestPortfolioAdjustmentRecords(
      @RequestParam(value = "pact_id", required = true) Long pactId) {
    return arService.getARLatestAdjustDate(pactId);
  }

  @GetMapping("/adjustment_record/latest_date_version")
  @Operation(summary = "Get the latest date and version of adjustment record by pact id.")
  AdjustmentRecord getLatestPortfolioAdjustmentRecord(
      @RequestParam(value = "pact_id", required = true) Long pactId) {
    return arService
        .getLatestSettledAR(pactId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("PortfolioAdjustmentRecord for portfolioPactId: %s not found", pactId)));
  }

  @GetMapping("/adjustment_records/latest_date_version")
  @Operation(summary = "Get the latest date and version of adjustment records by pact ids.")
  List<AdjustmentRecord> getLatestPortfolioAdjustmentRecords(
      @RequestParam(value = "pact_ids", required = true) List<Long> pactIds) {
    return arService.getLatestSettledARs(pactIds);
  }
}
