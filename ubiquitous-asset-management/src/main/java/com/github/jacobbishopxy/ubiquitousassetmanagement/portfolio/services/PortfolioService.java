/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.Overview;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioDetail;
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
  private PactService pactService;

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

  /**
   * Get a list of portfolios' overviews at latest date's latest version.
   * 
   * @param isActive
   * @return
   */
  public List<Overview> getPortfolioOverviews(Boolean isActive) {
    // get all activate(true)/inactivate(false)/all(null) portfolios' pacts
    List<Pact> pacts = pactService.getAllPacts(isActive);
    List<Integer> pactIds = pacts.stream().map(Pact::getId).collect(Collectors.toList());

    List<AdjustmentRecord> ars = adjustmentRecordService.getARsAtLatestAdjustDateVersion(pactIds);
    List<Integer> arIds = ars.stream().map(AdjustmentRecord::getId).collect(Collectors.toList());

    List<Performance> pfm = performanceService.getPerformancesByAdjustmentRecordIds(arIds);

    return pfm
        .stream()
        .map(p -> {
          Pact pact = p.getAdjustmentRecord().getPact();
          return Overview.fromPactAndPerformance(pact, p);
        })
        .collect(Collectors.toList());
  }

  /**
   * Get a sorted list of adjustment records by pact id.
   * 
   * @param pactId
   * @return
   */
  public List<AdjustmentRecord> getAdjustmentRecordsByPactId(Integer pactId) {
    return adjustmentRecordService.getARSortDesc(pactId);
  }

  /**
   * Get an overview of a portfolio by given adjustmentRecord id (id list can be
   * found in Pact entity).
   * 
   * @param adjustmentRecordId: nullable. if null means latest adjustment record
   * @return
   */
  public Optional<Overview> getPortfolioOverview(Integer adjustmentRecordId) {
    return adjustmentRecordService
        .getARById(adjustmentRecordId)
        .flatMap(ar -> {
          return performanceService
              .getPerformanceByAdjustmentRecordId(ar.getId())
              .map(p -> {
                Pact pact = ar.getPact();
                return Overview.fromPactAndPerformance(pact, p);
              });
        });
  }

  /**
   * Get a portfolio detail
   * 
   * @param pactId
   * @return
   */
  public PortfolioDetail getPortfolioLatestAdjustDateAndVersion(int pactId) {
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

    return new PortfolioDetail(ar, benchmarks, constituents, performance, adjustmentInfos);
  }

  public PortfolioDetail getPortfolioByAdjustmentRecordId(int adjustmentRecordId) {
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

    return new PortfolioDetail(ar, benchmarks, constituents, performance, adjustmentInfos);
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  /**
   * Settle a portfolio.
   * 
   * @param pactId
   * @param settleDate
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  public PortfolioDetail settle(int pactId, LocalDate settleDate) {
    // get pact
    Pact pact = pactService
        .getPactById(pactId)
        .orElseThrow(() -> new RuntimeException("No pact found for id: " + pactId));

    // get latest adjustment record
    AdjustmentRecord preAr = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new RuntimeException("No latest adjustment record found for pactId: " + pactId));

    // create a new adjustment record
    AdjustmentRecord localAr = new AdjustmentRecord();
    localAr.setPact(pact);
    localAr.setAdjustDate(preAr.getAdjustDate());
    if (settleDate.equals(preAr.getAdjustDate())) {
      localAr.setAdjustVersion(preAr.getAdjustVersion() + 1);
    } else {
      localAr.setAdjustVersion(1);
    }

    // newAr with Id returned by database
    AdjustmentRecord newAr = adjustmentRecordService.createAR(localAr);

    // copy benchmarks
    List<Benchmark> bms = benchmarkService.getBenchmarksByAdjustmentRecordId(preAr.getId());
    bms.stream().map(bm -> {
      bm.setId(null);
      bm.setAdjustmentRecord(newAr);
      bm.setAdjustDate(settleDate);
      return benchmarkService.createBenchmark(bm);
    });

    // copy constituents
    List<Constituent> cons = constituentService.getConstituentsByAdjustmentRecordId(preAr.getId());
    cons.stream().map(c -> {
      c.setId(null);
      c.setAdjustmentRecord(newAr);
      c.setAdjustDate(settleDate);
      return constituentService.createConstituent(c);
    });

    // copy performance
    Performance pfm = performanceService
        .getPerformanceByAdjustmentRecordId(preAr.getId())
        .orElse(new Performance());
    pfm.setAdjustmentRecord(newAr);
    performanceService.createPerformance(pfm);

    return new PortfolioDetail(newAr, bms, cons, pfm, null);
  }

  @Transactional(rollbackFor = Exception.class)
  public void cancelSettle(int pactId) {
    AdjustmentRecord ar = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new RuntimeException(
            "No latest adjustment record found for pactId: " + pactId));

    // delete the latest adjustment record
    adjustmentRecordService.deleteAR(ar.getId());

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
  public PortfolioDetail adjust(
      int pactId,
      LocalDate adjustDate,
      List<Constituent> constituents,
      List<Benchmark> benchmarks) {

    // 0. Find the latest adjustment record, if not found, create a new one. This
    // situation should only happen when the first time a portfolio is created.
    AdjustmentRecord latestAr = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseGet(() -> {
          // double check if pact exists
          Pact pact = pactService
              .getPactById(pactId)
              .orElseThrow(() -> new RuntimeException("No pact found for id: " + pactId));
          AdjustmentRecord ar = new AdjustmentRecord();
          ar.setPact(pact);
          ar.setAdjustDate(adjustDate);
          ar.setAdjustVersion(1);
          return adjustmentRecordService.createAR(ar);
        });

    // 1. check if adjustDate is the latest adjustDate
    PortfolioDetail sr = null;
    if (adjustDate != latestAr.getAdjustDate()) {
      // make a copy of the latest data and bind them to a new adjustment record
      sr = settle(pactId, adjustDate);
    } else {

      List<Constituent> latestCons = constituentService.getConstituentsByAdjustmentRecordId(latestAr.getId());

      // 2. according to the latest adjustment record, create AdjustmentInfos by
      // comparing the new constituents with the old ones
      List<AdjustmentInfo> ais = PortfolioAdjustmentHelper.adjust(latestCons, constituents);

      // 3. if adjustmentInfos is not empty, save them to the database
      if (!ais.isEmpty()) {
        adjustmentInfoService.createAdjustmentInfos(ais);
        // in addition, this means the portfolio is adjusted, so we need to settle it
        sr = settle(pactId, adjustDate);
      } else {
        // if no adjustmentInfos, simply regard it as a normal update
        List<Constituent> cons = constituentService.updateConstituents(constituents);

        // TODO:
        // !!! assign new data to sr
      }

    }

    return sr;
  }

}
