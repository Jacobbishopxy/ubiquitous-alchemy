/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PerformanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerformanceService {

  @Autowired
  private PerformanceRepository pRepo;

  // =======================================================================
  // Query methods
  //
  // expose to controller
  // =======================================================================

  public Optional<Performance> getPerformanceByAdjustmentRecordId(Long adjustmentRecordId) {
    return pRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public List<Performance> getPerformancesByAdjustmentRecordIds(List<Long> adjustmentRecordIds) {
    return pRepo.findByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

  // =======================================================================
  // Mutation methods
  //
  // called by internal services
  // =======================================================================

  public Performance createPerformance(Performance performance) {
    return pRepo.save(performance);
  }

  public Optional<Performance> updatePerformance(Long id, Performance performance) {
    return pRepo.findById(id).map(
        p -> {
          p.setBenchmarkEarningsYield(performance.getBenchmarkEarningsYield());
          p.setPortfolioEarningsYield(performance.getPortfolioEarningsYield());
          p.setAlpha(performance.getAlpha());
          return pRepo.save(p);
        });
  }

  public void deletePerformance(Long id) {
    pRepo.deleteById(id);
  }

  public void deletePerformanceByAdjustmentRecordId(Long adjustmentRecordId) {
    pRepo.deleteByAdjustmentRecordId(adjustmentRecordId);
  }

}
