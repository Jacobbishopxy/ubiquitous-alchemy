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
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.PortfolioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/portfolio_overview")
  List<Overview> getPortfolioOverviews(
      @RequestParam(value = "isActivate", required = false) boolean isActivate) {
    return portfolioService.getPortfolioOverviews(isActivate);
  }

  @GetMapping("/portfolio_by_pact_id")
  PortfolioDetail getPortfolioByPactId(@RequestParam("pactId") int pactId) {
    return portfolioService.getPortfolioLatestAdjustDateAndVersion(pactId);
  }

  @GetMapping("/portfolio_by_adjustment_record_id")
  PortfolioDetail getPortfolioByAdjustmentRecordId(@RequestParam("adjustmentRecordId") int adjustmentRecordId) {
    return portfolioService.getPortfolioByAdjustmentRecordId(adjustmentRecordId);
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

  @DeleteMapping("/portfolio_adjust")
  void cancelAdjustPortfolio(@RequestParam("pactId") int pactId) {
    portfolioService.cancelAdjust(pactId);
  }
}
