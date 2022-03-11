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

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PortfolioAdjustmentRecord", description = "PortfolioAdjustmentRecord related operations")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class AdjustmentRecordController {

  @Autowired
  private AdjustmentRecordService parService;

  @GetMapping(value = "/adjustment_record")
  List<AdjustmentRecord> getPortfolioAdjustmentRecords(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService.getPAR(portfolioPactId);
  }

  @GetMapping(value = "/adjustment_record_latest_date")
  List<AdjustmentRecord> getLatestPortfolioAdjustmentRecords(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService.getPARAtLatestAdjustDate(portfolioPactId);
  }

  @GetMapping(value = "/adjustment_record_latest_date_version")
  AdjustmentRecord getLatestPortfolioAdjustmentRecord(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService.getPARAtLatestAdjustDateAndVersion(portfolioPactId).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("PortfolioAdjustmentRecord for portfolioPactId: %s not found", portfolioPactId)));
  }
}
