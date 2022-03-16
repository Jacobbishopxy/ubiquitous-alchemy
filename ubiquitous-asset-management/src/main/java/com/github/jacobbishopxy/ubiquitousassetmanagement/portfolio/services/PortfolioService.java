/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.time.LocalDate;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioDto;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper.PortfolioAdjustmentHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PortfolioService
 * 
 * Full commands:
 * 
 * 1. Settle an existing portfolio. AdjustmentRecord/Performance is created. The
 * actual operation is to get the latest adjustment record's date and version,
 * and according to its id, copy the related portfolio benchmarks and
 * constituents to a new date. And then, create a new adjustment record.
 * 
 * 2. Cancel a portfolio settle. AdjustmentRecord/Performance is deleted. The
 * actual operation is to get the latest adjustment record's date and version,
 * and according to its id, delete the related portfolio benchmarks and
 * constituents. And then delete the adjustment record.
 * 
 * 3. Adjust an existing portfolio, which needs to be settled first (check
 * AdjustmentRecord's date and version). This operation will call `settle`
 * method automatically.
 * 
 * 4. Cancel an adjustment. Unsettle portfolio.
 */
@Service
public class PortfolioService {

  @Autowired
  private AdjustmentRecordService adjustmentRecordService;

  @Autowired
  private AdjustmentInfoService adjustmentInfoService;

  @Autowired
  private BenchmarkService benchmarkService;

  @Autowired
  private ConstituentService constituentService;

  @Autowired
  private PerformanceService performanceService;

  // =======================================================================
  // Query methods
  // =======================================================================

  public PortfolioDto getPortfolioLatestAdjustDateAndVersion(int pactId) {
    AdjustmentRecord ar = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new RuntimeException(
            "No latest adjustment record found for pactId: " + pactId));
    int arId = ar.getId();

    List<Benchmark> benchmarks = benchmarkService.getBenchmarksByAdjustmentRecordId(arId);
    List<Constituent> constituents = constituentService.getConstituentsByAdjustmentRecordId(arId);
    // if performance is null, return empty performance
    Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
    List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService.getAdjustmentInfosByAdjustmentRecordId(arId);

    return new PortfolioDto(ar, benchmarks, constituents, performance, adjustmentInfos);
  }

  public PortfolioDto getPortfolioByAdjustmentRecordId(int adjustmentRecordId) {
    AdjustmentRecord ar = adjustmentRecordService
        .getARById(adjustmentRecordId)
        .orElseThrow(() -> new RuntimeException(
            "No adjustment record found for id: " + adjustmentRecordId));
    int arId = ar.getId();

    List<Benchmark> benchmarks = benchmarkService.getBenchmarksByAdjustmentRecordId(arId);
    List<Constituent> constituents = constituentService.getConstituentsByAdjustmentRecordId(arId);
    // if performance is null, return empty performance
    Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
    List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService.getAdjustmentInfosByAdjustmentRecordId(arId);

    return new PortfolioDto(ar, benchmarks, constituents, performance, adjustmentInfos);
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @Transactional(rollbackFor = Exception.class)
  public PortfolioDto settle(int pactId, LocalDate settlementDate) {
    AdjustmentRecord preAr = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new RuntimeException("No latest adjustment record found for pactId: " + pactId));

    // pact with id
    Pact pact = new Pact();
    pact.setId(pactId);

    AdjustmentRecord localAr = new AdjustmentRecord();

    // create a new adjustment record
    localAr.setPact(pact);
    localAr.setAdjustDate(preAr.getAdjustDate());
    if (settlementDate.equals(preAr.getAdjustDate())) {
      localAr.setAdjustVersion(preAr.getAdjustVersion() + 1);
    } else {
      localAr.setAdjustVersion(1);
    }

    // newAr with Id returned by database
    AdjustmentRecord newAr = adjustmentRecordService.createPAR(localAr);

    // copy benchmarks
    List<Benchmark> bms = benchmarkService.getBenchmarksByAdjustmentRecordId(preAr.getId());
    bms.stream().map(bm -> {
      bm.setId(null);
      bm.setAdjustmentRecord(newAr);
      bm.setAdjustDate(settlementDate);
      return benchmarkService.createBenchmark(bm);
    });

    // copy constituents
    List<Constituent> cons = constituentService.getConstituentsByAdjustmentRecordId(preAr.getId());
    cons.stream().map(c -> {
      c.setId(null);
      c.setAdjustmentRecord(newAr);
      c.setAdjustDate(settlementDate);
      return constituentService.createConstituent(c);
    });

    // copy performance
    Performance pfm = performanceService
        .getPerformanceByAdjustmentRecordId(preAr.getId())
        .orElse(new Performance());
    pfm.setAdjustmentRecord(newAr);
    performanceService.createPerformance(pfm);

    return new PortfolioDto(newAr, bms, cons, pfm, null);
  }

  @Transactional(rollbackFor = Exception.class)
  public void cancelSettle(int pactId) {
    AdjustmentRecord ar = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new RuntimeException("No latest adjustment record found for pactId: " + pactId));

    // delete the latest adjustment record
    adjustmentRecordService.deletePAR(ar.getId());

    // delete benchmarks
    benchmarkService.deleteBenchmarksByAdjustmentRecordId(ar.getId());

    // delete constituents
    constituentService.deleteConstituentsByAdjustmentRecordId(ar.getId());

    // delete performance
    performanceService.deletePerformanceByAdjustmentRecordId(ar.getId());
  }

  // automation process:
  // 1. settle
  // 2. adjust -> AdjustmentInfo
  // 3. settle
  public void adjust(
      int pactId,
      LocalDate adjustDate,
      List<Constituent> constituents,
      List<Benchmark> benchmarks) {

    // 1. make a copy of the latest data and bind them to a new adjustment record
    // TODO:
    // check if the portfolio has already been settled; if settled, use the settled
    // ones.
    // ASKING: how to distinguish if the portfolio is settled?
    PortfolioDto sr = settle(pactId, adjustDate);

    // 2. according to the latest adjustment record, create AdjustmentInfos by
    // comparing the new constituents with the old ones
    List<AdjustmentInfo> ais = PortfolioAdjustmentHelper.adjust(sr.constituents(), constituents);
    adjustmentInfoService.createAdjustmentInfos(ais);

    // TODO:
    // now we've saved the adjustment info, next step is to settle the new portfolio

  }

  public void cancelAdjust(int pactId) {

    // TODO:
  }
}
