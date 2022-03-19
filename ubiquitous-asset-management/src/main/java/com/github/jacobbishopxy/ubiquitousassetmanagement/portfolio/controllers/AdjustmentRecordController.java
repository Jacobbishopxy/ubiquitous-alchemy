/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.AdjustmentRecordService;

import org.springframework.beans.factory.annotation.Autowired;
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
  private AdjustmentRecordService parService;

  // =======================================================================
  // Query methods
  // =======================================================================

  // TODO:
  @GetMapping("/adjustment_records")
  @Operation(description = "Get all adjustment records by pact id with pagination.")
  List<AdjustmentRecord> getPortfolioAdjustmentRecords(
      @RequestParam(value = "pactId", required = true) int pactId) {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented yet");
  }

  @GetMapping(value = "/adjustment_record/{id}")
  @Operation(description = "Get adjustment record by id.")
  AdjustmentRecord getPortfolioAdjustmentRecord(@PathVariable("id") int id) {
    return parService
        .getARById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("AdjustmentRecord for id: %s not found", id)));
  }

  @GetMapping(value = "/adjustment_records_latest_date")
  @Operation(description = "Get the latest date of adjustment records.")
  List<AdjustmentRecord> getLatestPortfolioAdjustmentRecords(
      @RequestParam(value = "pact_id", required = true) Integer pactId) {
    return parService.getARAtLatestAdjustDate(pactId);
  }

  @GetMapping(value = "/adjustment_record_latest_date_version")
  @Operation(description = "Get the latest date and version of adjustment record.")
  AdjustmentRecord getLatestPortfolioAdjustmentRecord(
      @RequestParam(value = "pact_id", required = true) Integer pactId) {
    return parService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("PortfolioAdjustmentRecord for portfolioPactId: %s not found", pactId)));
  }

  @GetMapping(value = "/adjustment_records_latest_date_version")
  List<AdjustmentRecord> getLatestPortfolioAdjustmentRecords(
      @RequestParam(value = "pact_id", required = true) List<Integer> pactIds) {
    return parService.getARsAtLatestAdjustDateVersion(pactIds);
  }
}
