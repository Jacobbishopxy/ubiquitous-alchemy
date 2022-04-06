/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controller;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.PortfolioDetail;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.PortfolioOverview;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.portfolioActions.SettlePortfolio;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.PortfolioService;

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
  PortfolioOverview getPortfolioOverview(
      @RequestParam(value = "adjustment_record_id", required = true) Long adjustmentRecordId) {
    return portfolioService
        .getPortfolioOverview(adjustmentRecordId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("AdjustmentRecordId id: %s not found", adjustmentRecordId)));
  }

  @GetMapping("/portfolio_adjustment_records")
  @Operation(summary = "Get all adjustment records by pact id.")
  List<AdjustmentRecord> getAdjustmentRecordsByPactId(
      @RequestParam(value = "pact_id", required = true) Long pactId,
      @RequestParam(value = "is_adjusted", required = false) Boolean isAdjusted,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size) {

    PageRequest pr = null;
    if (page != null && size != null) {
      pr = PageRequest.of(page, size);
    }

    return portfolioService.getAdjustmentRecordsByPactId(pactId, pr, isAdjusted);
  }

  @GetMapping("/portfolio_detail/unsettled")
  @Operation(summary = "Get unsettled portfolio detail by pact id.")
  PortfolioDetail getUnsettledPortfolioDetail(
      @RequestParam(value = "pact_id", required = true) Long pactId) {
    return portfolioService.getUnsettledPortfolioDetail(pactId);
  }

  @GetMapping("/portfolio_detail/latest_settled")
  @Operation(summary = "Get latest settled portfolio detail by pact id.")
  PortfolioDetail getLatestSettledPortfolioByPactId(
      @RequestParam(value = "pact_id", required = true) Long pactId) {
    return portfolioService.getLatestSettledPortfolioDetail(pactId);
  }

  @GetMapping("/portfolio_detail/history")
  @Operation(summary = "Get portfolio detail by adjustment record id.")
  PortfolioDetail getPortfolioByPactId(
      @RequestParam(value = "adjustment_record_id", required = true) Long adjustmentRecordId) {
    return portfolioService.getPortfolioDetailByARId(adjustmentRecordId);

  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/portfolio_action/settle")
  @Operation(summary = "Settle a portfolio.")
  PortfolioDetail settlePortfolio(@RequestBody SettlePortfolio portfolioSettle) {
    return portfolioService.settle(
        portfolioSettle.pactId(),
        portfolioSettle.settlementDate());
  }

  @DeleteMapping("/portfolio_action/settle")
  @Operation(summary = "Cancel a portfolio settlement.")
  void cancelSettlePortfolio(@RequestParam("pact_id") Long pactId) {
    portfolioService.cancelSettle(pactId);
  }

}
