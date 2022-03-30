/**
 * Created by Jacob Xie on 3/28/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AdjustmentRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

  @Autowired
  private AdjustmentRecordRepository arRepo;

  public void checkAdjustmentRecordIsUnsettled(Long adjustmentRecordId) {
    AdjustmentRecord ar = arRepo
        .findById(adjustmentRecordId)
        .orElseThrow(() -> new RuntimeException(
            String.format("AdjustmentRecord %d not found", adjustmentRecordId)));

    if (ar.getIsUnsettled() != true) {
      throw new RuntimeException(String.format(
          "AdjustmentRecord %d is settled, can no longer be modified. Use `unsettle` API unless settlement undo is required.",
          adjustmentRecordId));
    }
  }

  public void checkBenchmarksTotalWeightIsWithinRange(List<Benchmark> benchmarks) {
    double totalWeight = benchmarks
        .stream()
        .mapToDouble(Benchmark::getStaticWeight)
        .sum();

    if (totalWeight < 0 || totalWeight > 1.1) {
      throw new RuntimeException(String.format(
          "Total weight of benchmarks is %f, which is out of range [0, 1]", totalWeight));
    }
  }

  public void checkConstituentsTotalWeightIsWithinRange(List<Constituent> constituents) {
    double totalWeight = constituents
        .stream()
        .mapToDouble(Constituent::getStaticWeight)
        .sum();

    if (totalWeight < 0 || totalWeight > 1.1) {
      throw new RuntimeException(String.format(
          "Total weight of constituents is %f, which is out of range [0, 1]", totalWeight));
    }
  }
}
