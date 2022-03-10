/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioAdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.PortfolioAdjustmentRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PortfolioAdjustmentRecord", description = "PortfolioAdjustmentRecord related operations")
@RestController
@RequestMapping("v1")
public class PortfolioAdjustmentRecordController {

  @Autowired
  private PortfolioAdjustmentRecordService parService;

  @GetMapping(value = "/portfolio_adjustment_records")
  List<PortfolioAdjustmentRecord> getPortfolioAdjustmentRecords(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService.getPortfolioAdjustmentRecord(portfolioPactId);
  }

  @GetMapping(value = "/portfolio_adjustment_records/latest")
  List<PortfolioAdjustmentRecord> getLatestPortfolioAdjustmentRecord(
      @RequestParam(value = "portfolioPactId", required = true) Integer portfolioPactId) {
    return parService.getPortfolioAdjustmentRecordAtLatestAdjustDate(portfolioPactId);
  }

}
