/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.Overview;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioAdjust;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioDetail;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioSettle;
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
  List<Overview> getPortfolioOverviews(
      @RequestParam(value = "isActivate", required = false) boolean isActivate) {
    return portfolioService.getPortfolioOverviews(isActivate);
  }

  @GetMapping("/portfolio_overview")
  Overview getPortfolioOverview(@RequestParam("adjustmentRecordId") int adjustmentRecordId) {
    return portfolioService
        .getPortfolioOverview(adjustmentRecordId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("AdjustmentRecordId id: %s not found", adjustmentRecordId)));
  }

  @GetMapping("/portfolio_adjustment_records")
  List<AdjustmentRecord> getAdjustmentRecordsByPactId(
      @RequestParam(value = "pactId", required = true) int pactId) {
    return portfolioService.getAdjustmentRecordsByPactId(pactId);
  }

  @GetMapping("/portfolio_detail")
  PortfolioDetail getPortfolioByPactId(
      @RequestParam(value = "pactId", required = false) Integer pactId,
      @RequestParam(value = "adjustmentRecordId", required = false) Integer adjustmentRecordId) {

    if (pactId != null) {
      return portfolioService.getPortfolioLatestAdjustDateAndVersion(pactId);
    } else if (adjustmentRecordId != null) {
      return portfolioService.getPortfolioByAdjustmentRecordId(adjustmentRecordId);
    } else {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "pactId or adjustmentRecordId must be provided");
    }
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/portfolio_settle")
  void settlePortfolio(@RequestBody PortfolioSettle portfolioSettle) {
    portfolioService.settle(
        portfolioSettle.pactId(),
        portfolioSettle.settlementDate());
  }

  @DeleteMapping("/portfolio_settle")
  void cancelSettlePortfolio(@RequestParam("pactId") int pactId) {
    portfolioService.cancelSettle(pactId);
  }

  @Operation(description = "A powerful method to adjust portfolio. ")
  @PostMapping("/portfolio_adjust")
  void adjustPortfolio(
      @RequestParam("pactId") int pactId,
      @RequestBody PortfolioAdjust portfolioAdjust) {
    portfolioService.adjust(
        pactId,
        portfolioAdjust.adjustDate(),
        portfolioAdjust.constituents(),
        portfolioAdjust.benchmarks());
  }
}
