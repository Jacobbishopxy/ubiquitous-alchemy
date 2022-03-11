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

  public List<Benchmark> getPortfolioBenchmarks(int adjustmentRecordId) {
    return pbRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public List<Benchmark> getPortfolioBenchmarks(List<Integer> adjustmentRecordIds) {
    return pbRepo.findByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

  public Benchmark createPortfolioBenchmark(Benchmark portfolioBenchmark) {
    return pbRepo.save(portfolioBenchmark);
  }

  public Optional<Benchmark> updatePortfolioBenchmark(int id, Benchmark portfolioBenchmark) {
    return pbRepo.findById(id).map(
        record -> {
          record.setAdjustDate(portfolioBenchmark.getAdjustDate());
          record.setBenchmarkName(portfolioBenchmark.getBenchmarkName());
          record.setPercentageChange(portfolioBenchmark.getPercentageChange());
          record.setAdjustDateWeight(portfolioBenchmark.getAdjustDateWeight());
          record.setCurrentWeight(portfolioBenchmark.getCurrentWeight());
          return pbRepo.save(record);
        });
  }

  public void deletePortfolioBenchmark(int id) {
    pbRepo.deleteById(id);
  }
}
