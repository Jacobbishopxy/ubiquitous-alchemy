/**
 * Created by Jacob Xie on 3/11/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.BenchmarkRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PerformanceRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper.PortfolioCalculationHelper;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper.PortfolioCalculationHelper.BenchmarksResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BenchmarkService {

  @Autowired
  private BenchmarkRepository bRepo;

  @Autowired
  private PerformanceRepository pRepo;

  // =======================================================================
  // Query methods
  //
  // expose to controller
  // =======================================================================

  public List<Benchmark> getBenchmarksByAdjustmentRecordId(int adjustmentRecordId) {
    return bRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public List<Benchmark> getBenchmarksByAdjustmentRecordIds(List<Integer> adjustmentRecordIds) {
    return bRepo.findByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

  public Optional<Benchmark> getBenchmarkById(int id) {
    return bRepo.findById(id);
  }

  // =======================================================================
  // Mutation methods
  //
  // called by internal services
  // =======================================================================

  // common mutations is called when create/update/delete a benchmark
  @Transactional(rollbackFor = Exception.class)
  private void commonMutation(int adjustmentRecordId) {
    // 1. find all benchmarks in the portfolio
    List<Benchmark> benchmarks = getBenchmarksByAdjustmentRecordId(adjustmentRecordId);

    // 2. recalculate all benchmarks and their related performance
    BenchmarksResult res = PortfolioCalculationHelper
        .modifyBenchmarksAndCalculateBenchmarkEarningsYield(benchmarks);

    // 3. update all benchmarks' dynamic weight
    bRepo.saveAll(res.benchmarks());

    // 4. update or create performance
    Performance performance = pRepo
        .findByAdjustmentRecordId(adjustmentRecordId)
        .orElse(new Performance());
    performance.setBenchmarkEarningsYield(res.earningsYield());
    performance.setAlpha(performance.getPortfolioEarningsYield() - res.earningsYield());
    pRepo.save(performance);
  }

  @Transactional(rollbackFor = Exception.class)
  public Benchmark createBenchmark(Benchmark benchmark) {
    // 0. validate benchmark
    // IMPORTANT: benchmark's adjustmentRecord id cannot be null. In other words,
    // it must have an adjustmentRecord to create a benchmark.
    int adjustmentRecordId = benchmark.get_adjustment_record_id();

    // 1. save benchmark.
    Benchmark newB = bRepo.save(benchmark);

    // 2. common mutation
    commonMutation(adjustmentRecordId);

    return bRepo
        .findById(newB.getId())
        .orElseThrow(() -> new RuntimeException(
            String.format("Constituent %d not found", newB.getId())));
  }

  @Transactional(rollbackFor = Exception.class)
  public Optional<Benchmark> updateBenchmark(int id, Benchmark portfolioBenchmark) {
    // 0. validate benchmark
    int adjustmentRecordId = portfolioBenchmark.get_adjustment_record_id();

    // 1. update benchmark
    bRepo
        .findById(id)
        .map(
            record -> {
              record.setAdjustDate(portfolioBenchmark.getAdjustDate());
              record.setBenchmarkName(portfolioBenchmark.getBenchmarkName());
              record.setPercentageChange(portfolioBenchmark.getPercentageChange());
              record.setStaticWeight(portfolioBenchmark.getStaticWeight());
              return bRepo.save(record);
            })
        .orElseThrow(() -> new RuntimeException(
            String.format("Benchmark %d not found", id)));

    // 2. common mutation
    commonMutation(adjustmentRecordId);

    return bRepo.findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteBenchmark(int id) {
    // 0. validate benchmark
    Benchmark b = bRepo
        .findById(id)
        .orElseThrow(() -> new RuntimeException(
            String.format("Benchmark %d not found", id)));
    int adjustmentRecordId = b.get_adjustment_record_id();

    // 1. delete benchmark
    bRepo.deleteById(id);

    // 2. common mutation
    commonMutation(adjustmentRecordId);
  }

  // delete all benchmarks in the portfolio
  // IMPORTANT: adjustRecord is not deleted
  @Transactional(rollbackFor = Exception.class)
  public void deleteBenchmarksByAdjustmentRecordId(int adjustmentRecordId) {
    bRepo.deleteByAdjustmentRecordId(adjustmentRecordId);

    pRepo.deleteByAdjustmentRecordId(adjustmentRecordId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteBenchmarksByAdjustmentRecordIds(List<Integer> adjustmentRecordIds) {
    bRepo.deleteByAdjustmentRecordIdIn(adjustmentRecordIds);

    pRepo.deleteByAdjustmentRecordIdIn(adjustmentRecordIds);
  }
}
