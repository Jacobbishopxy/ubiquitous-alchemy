/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioOverview;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.AdjustmentAvailable;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioDetail;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.BenchmarkRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.ConstituentRepository;
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
  private BenchmarkRepository benchmarkRepository;

  @Autowired
  private BenchmarkService benchmarkService;

  @Autowired
  private ConstituentRepository constituentRepository;

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
  public List<PortfolioOverview> getPortfolioOverviews(Boolean isActive) {
    // get all activate(true)/inactivate(false)/all(null) portfolios' pacts
    List<Pact> pacts = pactService.getAllPacts(isActive);
    List<Long> pactIds = pacts.stream().map(Pact::getId).collect(Collectors.toList());

    List<AdjustmentRecord> ars = adjustmentRecordService.getARsAtLatestAdjustDateVersion(pactIds);
    List<Long> arIds = ars.stream().map(AdjustmentRecord::getId).collect(Collectors.toList());

    List<Performance> pfm = performanceService.getPerformancesByAdjustmentRecordIds(arIds);

    return pfm
        .stream()
        .map(p -> {
          Pact pact = p.getAdjustmentRecord().getPact();
          return PortfolioOverview.fromPactAndPerformance(pact, p);
        })
        .collect(Collectors.toList());
  }

  /**
   * Get a sorted list of adjustment records by pact id.
   *
   * @param pactId
   * @return
   */
  public List<AdjustmentRecord> getAdjustmentRecordsByPactId(Long pactId) {
    return adjustmentRecordService.getARSortDesc(pactId);
  }

  /**
   * Get an overview of a portfolio by given adjustmentRecord id (id list can be
   * found in Pact entity).
   *
   * @param adjustmentRecordId: nullable. if null means latest adjustment record
   * @return
   */
  public Optional<PortfolioOverview> getPortfolioOverview(Long adjustmentRecordId) {
    return adjustmentRecordService
        .getARById(adjustmentRecordId)
        .flatMap(ar -> {
          return performanceService
              .getPerformanceByAdjustmentRecordId(ar.getId())
              .map(p -> {
                Pact pact = ar.getPact();
                return PortfolioOverview.fromPactAndPerformance(pact, p);
              });
        });
  }

  /**
   * Get a portfolio detail
   *
   * @param pactId
   * @return
   */
  public PortfolioDetail getPortfolioLatestAdjustDateAndVersion(Long pactId) {
    AdjustmentRecord ar = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new RuntimeException(
            "No latest adjustment record found for pactId: " + pactId));
    Long arId = ar.getId();

    List<Benchmark> benchmarks = benchmarkService.getBenchmarksByAdjustmentRecordId(arId);
    List<Constituent> constituents = constituentService.getConstituentsByAdjustmentRecordId(arId);
    // if performance is null, return empty performance
    Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
    List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService.getAdjustmentInfosByAdjustmentRecordId(arId);

    return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos);
  }

  public PortfolioDetail getPortfolioByAdjustmentRecordId(Long adjustmentRecordId) {
    AdjustmentRecord ar = adjustmentRecordService
        .getARById(adjustmentRecordId)
        .orElseThrow(() -> new RuntimeException(
            "No adjustment record found for id: " + adjustmentRecordId));
    Long arId = ar.getId();

    List<Benchmark> benchmarks = benchmarkService.getBenchmarksByAdjustmentRecordId(arId);
    List<Constituent> constituents = constituentService.getConstituentsByAdjustmentRecordId(arId);
    // if performance is null, return empty performance
    Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
    List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService.getAdjustmentInfosByAdjustmentRecordId(arId);

    return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos);
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
  public PortfolioDetail settle(Long pactId, LocalDate settleDate) {
    // get pact
    Pact pact = pactService
        .getPactById(pactId)
        .orElseThrow(() -> new RuntimeException("No pact found for id: " + pactId));

    // get latest adjustment record
    AdjustmentRecord preAr = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseThrow(() -> new RuntimeException("No latest adjustment record found for pactId: " + pactId));

    // prohibit settle if the settle date is before the latest adjustment record's
    // date
    if (settleDate.isBefore(preAr.getAdjustDate())) {
      throw new RuntimeException(
          String.format(
              "Settle date: %s is before latest adjustment record's date: %s",
              settleDate,
              preAr.getAdjustDate()));
    }

    // create a new adjustment record
    AdjustmentRecord localAr = new AdjustmentRecord();
    localAr.setId(null);
    localAr.setPact(pact);
    localAr.setAdjustDate(settleDate);
    if (settleDate.equals(preAr.getAdjustDate())) {
      localAr.setAdjustVersion(preAr.getAdjustVersion() + 1);
    } else {
      localAr.setAdjustVersion(1);
    }

    // create new adjustment record and save
    AdjustmentRecord newAr = adjustmentRecordService.createAR(localAr);

    // TODO:
    // all the copy operations should be simplified.

    // copy benchmarks and save
    List<Benchmark> bms = benchmarkService
        .getBenchmarksByAdjustmentRecordId(preAr.getId())
        .stream()
        .map(bm -> {
          Benchmark newBm = new Benchmark();
          newBm.setAdjustmentRecord(newAr);
          newBm.setAdjustDate(bm.getAdjustDate());
          newBm.setBenchmarkName(bm.getBenchmarkName());
          newBm.setSymbol(bm.getSymbol());
          newBm.setPercentageChange(bm.getPercentageChange());
          newBm.setStaticWeight(bm.getStaticWeight());
          newBm.setDynamicWeight(bm.getDynamicWeight());
          return newBm;
        })
        .collect(Collectors.toList());
    // IMPORTANT: instead of using use benchmarkService here, we use
    // benchmarkRepository, because the former method will cause additional effect
    bms = benchmarkRepository.saveAll(bms);

    // copy constituents and save
    List<Constituent> cons = constituentService
        .getConstituentsByAdjustmentRecordId(preAr.getId())
        .stream()
        .map(c -> {
          Constituent newC = new Constituent();
          newC.setAdjustmentRecord(newAr);
          newC.setAdjustDate(c.getAdjustDate());
          newC.setSymbol(c.getSymbol());
          newC.setAbbreviation(c.getAbbreviation());
          newC.setAdjustDatePrice(c.getAdjustDatePrice());
          newC.setCurrentPrice(c.getCurrentPrice());
          newC.setAdjustDateFactor(c.getAdjustDateFactor());
          newC.setCurrentFactor(c.getCurrentFactor());
          newC.setStaticWeight(c.getStaticWeight());
          newC.setDynamicWeight(c.getDynamicWeight());
          newC.setPbpe(c.getPbpe());
          newC.setMarketValue(c.getMarketValue());
          newC.setEarningsYield(c.getEarningsYield());
          return newC;
        })
        .collect(Collectors.toList());
    // IMPORTANT: instead of using use performanceService here, we use
    // constituentRepository, because the former method will cause additional effect
    cons = constituentRepository.saveAll(cons);

    // copy performance and save
    Performance pfm = performanceService
        .getPerformanceByAdjustmentRecordId(preAr.getId())
        .orElseGet(() -> {
          Performance p = new Performance();
          p.setAdjustmentRecord(newAr);
          return p;
        });
    Performance newPfm = new Performance();
    newPfm.setAdjustmentRecord(newAr);
    newPfm.setPortfolioEarningsYield(pfm.getPortfolioEarningsYield());
    newPfm.setBenchmarkEarningsYield(pfm.getBenchmarkEarningsYield());
    newPfm.setAlpha(pfm.getAlpha());
    // No other side effect, simply use performanceService
    performanceService.createPerformance(newPfm);

    return new PortfolioDetail(newAr, cons, bms, pfm, null);
  }

  @Transactional(rollbackFor = Exception.class)
  public void cancelSettle(Long pactId) {
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
      Long pactId,
      LocalDate adjustDate,
      List<Constituent> constituents,
      List<Benchmark> benchmarks) {

    // 0. Find the latest adjustment record, if not found, create a new one. This
    // situation should only happen when the first time a portfolio is created.
    AdjustmentRecord latestAr = adjustmentRecordService
        .getARAtLatestAdjustDateAndVersion(pactId)
        .orElseGet(() -> {
          // double check if pact exists. This situation should not happen, since every
          // time we create a new pact, a new adjustment record is created.
          Pact pact = pactService
              .getPactById(pactId)
              .orElseThrow(() -> new RuntimeException("No pact found for id: " + pactId));
          AdjustmentRecord ar = new AdjustmentRecord();
          ar.setPact(pact);
          ar.setAdjustDate(adjustDate);
          ar.setAdjustVersion(1);
          return adjustmentRecordService.createAR(ar);
        });

    // prohibit settle if the settle date is before the latest adjustment record's
    // date
    if (adjustDate.isBefore(latestAr.getAdjustDate())) {
      throw new RuntimeException(
          String.format(
              "Adjust date: %s is before latest adjustment record's date: %s",
              adjustDate,
              latestAr.getAdjustDate()));
    }

    // 1. check if adjustDate is the latest adjustDate
    PortfolioDetail sr = null;
    if (adjustDate != latestAr.getAdjustDate()) {
      // if not the latest adjustDate, make a copy of the latest data and bind them to
      // a new adjustment record
      sr = settle(pactId, adjustDate);
      // update latest adjustment record
      latestAr = sr.adjustmentRecord();
    }

    // 2. according to the latest adjustment record, create AdjustmentInfos by
    // comparing the new constituents with the old ones
    List<Constituent> latestCons = constituentService.getConstituentsByAdjustmentRecordId(latestAr.getId());
    List<AdjustmentInfo> ais = PortfolioAdjustmentHelper.adjust(latestCons, constituents);

    // 3. if adjustmentInfos is not empty, save them to the database
    if (!ais.isEmpty()) {
      adjustmentInfoService.createAdjustmentInfos(ais);
      // in addition, this means the portfolio is adjusted, so we need to settle it
      sr = settle(pactId, adjustDate);
    } else {
      // if no adjustmentInfos, simply regard it as a normal update
      // update constituents
      List<Constituent> cons = constituentService.updateConstituents(constituents);
      // update benchmarks
      List<Benchmark> bms = benchmarkService.updateBenchmarks(benchmarks);
      // get performance
      Performance pfm = performanceService.getPerformanceByAdjustmentRecordId(latestAr.getId()).get();

      sr = new PortfolioDetail(
          latestAr,
          cons,
          bms,
          pfm,
          List.of());
    }

    return sr;
  }

  public AdjustmentAvailable checkAdjustmentAvailable(Long pactId, LocalDate adjustDate) {
    // Here use strict `.get()` means we don't need to worry about null pointer
    // exception (see line 265).
    AdjustmentRecord latestAr = adjustmentRecordService.getARAtLatestAdjustDateAndVersion(pactId).get();

    Boolean isAvailable = false;
    // Simply regard adjustment is available if the adjustDate is the same as latest
    // adjustDate
    if (adjustDate.equals(latestAr.getAdjustDate())) {
      isAvailable = true;
    }

    return new AdjustmentAvailable(isAvailable, adjustDate, latestAr.getAdjustDate(), latestAr.getAdjustVersion());
  }

}
