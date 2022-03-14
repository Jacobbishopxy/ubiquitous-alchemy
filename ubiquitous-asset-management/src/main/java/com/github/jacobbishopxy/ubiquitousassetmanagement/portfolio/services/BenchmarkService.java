/**
 * Created by Jacob Xie on 3/11/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.BenchmarkRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenchmarkService {

  @Autowired
  private BenchmarkRepository pbRepo;

  public List<Benchmark> getBenchmarksByAdjustmentRecordId(int adjustmentRecordId) {
    return pbRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public List<Benchmark> getBenchmarksByAdjustmentRecordIds(List<Integer> adjustmentRecordIds) {
    return pbRepo.findByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

  public Optional<Benchmark> getBenchmarkById(int id) {
    return pbRepo.findById(id);
  }

  public Benchmark createBenchmark(Benchmark portfolioBenchmark) {
    return pbRepo.save(portfolioBenchmark);
  }

  public Optional<Benchmark> updateBenchmark(int id, Benchmark portfolioBenchmark) {
    return pbRepo.findById(id).map(
        record -> {
          record.setAdjustDate(portfolioBenchmark.getAdjustDate());
          record.setBenchmarkName(portfolioBenchmark.getBenchmarkName());
          record.setPercentageChange(portfolioBenchmark.getPercentageChange());
          record.setStaticWeight(portfolioBenchmark.getStaticWeight());
          record.setDynamicWeight(portfolioBenchmark.getDynamicWeight());
          return pbRepo.save(record);
        });
  }

  public void deleteBenchmark(int id) {
    pbRepo.deleteById(id);
  }

  public void deleteBenchmarksByAdjustmentRecordId(int adjustmentRecordId) {
    pbRepo.deleteByAdjustmentRecordId(adjustmentRecordId);
  }

  public void deleteBenchmarksByAdjustmentRecordIds(List<Integer> adjustmentRecordIds) {
    pbRepo.deleteByAdjustmentRecordIdIn(adjustmentRecordIds);
  }
}
