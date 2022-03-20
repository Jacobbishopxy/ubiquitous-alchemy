/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioOverview;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.portfolioActions.AdjustPortfolio;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.portfolioActions.SettlePortfolio;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioDetail;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.PortfolioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class PortfolioController {

  @Autowired
  private PortfolioService portfolioService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/portfolio_overviews")
  @Operation(summary = "Get all portfolio overviews.")
  List<PortfolioOverview> getPortfolioOverviews(
      @RequestParam(value = "is_activate", required = false) boolean isActivate) {
    return portfolioService.getPortfolioOverviews(isActivate);
  }

  @GetMapping("/portfolio_overview")
  @Operation(summary = "Get portfolio overview by id.")
  PortfolioOverview getPortfolioOverview(@RequestParam("adjustment_record_id") int adjustmentRecordId) {
    return portfolioService
        .getPortfolioOverview(adjustmentRecordId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("AdjustmentRecordId id: %s not found", adjustmentRecordId)));
  }

  @GetMapping("/portfolio_adjustment_records")
  @Operation(summary = "Get all adjustment records by pact id.")
  List<AdjustmentRecord> getAdjustmentRecordsByPactId(
      @RequestParam(value = "pact_id", required = true) int pactId) {
    return portfolioService.getAdjustmentRecordsByPactId(pactId);
  }

  @GetMapping("/portfolio_detail")
  @Operation(summary = "Get portfolio detail by either pact id or adjustment record id.")
  PortfolioDetail getPortfolioByPactId(
      @RequestParam(value = "pact_id", required = false) Integer pactId,
      @RequestParam(value = "adjustment_record_id", required = false) Integer adjustmentRecordId) {

    if (pactId != null) {
      return portfolioService.getPortfolioLatestAdjustDateAndVersion(pactId);
    } else if (adjustmentRecordId != null) {
      return portfolioService.getPortfolioByAdjustmentRecordId(adjustmentRecordId);
    } else {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "pact_id or adjustment_record_id is required");
    }
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/portfolio_action/settle")
  @Operation(summary = "Settle a portfolio.")
  void settlePortfolio(@RequestBody SettlePortfolio portfolioSettle) {
    portfolioService.settle(
        portfolioSettle.pactId(),
        portfolioSettle.settlementDate());
  }

  @DeleteMapping("/portfolio_action/settle")
  @Operation(summary = "Cancel a portfolio settlement.")
  void cancelSettlePortfolio(@RequestParam("pact_id") int pactId) {
    portfolioService.cancelSettle(pactId);
  }

  @PostMapping("/portfolio_action/adjust")
  @Operation(summary = "Adjust a portfolio.")
  void adjustPortfolio(
      @RequestParam("pact_id") int pactId,
      @RequestBody AdjustPortfolio portfolioAdjust) {
    portfolioService.adjust(
        pactId,
        portfolioAdjust.adjustDate(),
        portfolioAdjust.constituents(),
        portfolioAdjust.benchmarks());
  }

  @DeleteMapping("/portfolio_action/adjust")
  @Operation(summary = "Cancel a portfolio adjustment.")
  void cancelAdjustPortfolio(@RequestParam("pact_id") int pactId) {
    portfolioService.cancelSettle(pactId);
  }
}
