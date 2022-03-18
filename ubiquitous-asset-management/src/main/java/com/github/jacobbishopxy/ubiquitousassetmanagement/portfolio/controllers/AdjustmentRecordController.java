/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.FullAdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.AdjustmentRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class AdjustmentRecordController {

  @Autowired
  private AdjustmentRecordService parService;

  @GetMapping(value = "/adjustment_record")
  List<AdjustmentRecord> getPortfolioAdjustmentRecords(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService.getARByPactId(portfolioPactId);
  }

  @GetMapping(value = "/adjustment_record/latest_date")
  List<AdjustmentRecord> getLatestPortfolioAdjustmentRecords(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService.getARAtLatestAdjustDate(portfolioPactId);
  }

  @GetMapping(value = "/adjustment_record/latest_date_version")
  FullAdjustmentRecord getLatestPortfolioAdjustmentRecord(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService
        .getARAtLatestAdjustDateAndVersion(portfolioPactId)
        .map(ar -> {
          return FullAdjustmentRecord.fromAdjustmentRecord(ar);
        })
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("PortfolioAdjustmentRecord for portfolioPactId: %s not found", portfolioPactId)));
  }

  @GetMapping(value = "/adjustment_records/latest_date_version")
  List<AdjustmentRecord> getLatestPortfolioAdjustmentRecords(
      @RequestParam(value = "portfolioPactIds", required = true) List<Integer> portfolioPactIds) {
    return parService.getARsAtLatestAdjustDateVersion(portfolioPactIds);
  }
}
