/**
 * Created by Jacob Xie on 5/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AccumulatedPerformance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AccumulatedPerformanceRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AdjustmentRecordRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.PerformanceRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.helper.PortfolioCalculationHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecalculateService {

  @Autowired
  private AdjustmentRecordRepository adjustmentRecordRepository;

  @Autowired
  private PerformanceRepository performanceRepository;

  @Autowired
  private AccumulatedPerformanceRepository accumulatedPerformanceRepository;

  // =======================================================================
  // Mutation methods
  // =======================================================================

  /**
   * Recalculate accumulated performance for a portfolio.
   *
   * Every time a settle/unsettle action is made, the accumulated performance
   * should be recalculated.
   *
   * @param pact
   * @param isAdjusted
   * @return
   */
  public AccumulatedPerformance recalculateAccumulatedPerformance(Pact pact, boolean isSettle, boolean isAdjusted) {
    Long pactId = pact.getId();

    // get all adjustment record ids under this pact
    List<Long> arIds = adjustmentRecordRepository.findIdsByPactId(pactId);
    // get all performance records
    List<Performance> pfms = performanceRepository.findByAdjustmentRecordIdIn(arIds);
    // calculate accumulated performance
    PortfolioCalculationHelper.AccumulatedPerformanceResult apr = PortfolioCalculationHelper
        .calculateAccumulatedPerformance(pfms);
    // query accumulated performance or create a new one
    AccumulatedPerformance ap = accumulatedPerformanceRepository
        .findByPactId(pactId)
        .orElseGet(() -> {
          AccumulatedPerformance nAp = new AccumulatedPerformance();
          nAp.setPact(pact);
          nAp.setAdjustCount(0);
          return nAp;
        });

    if (isAdjusted) {
      if (isSettle) {
        ap.setAdjustCount(ap.getAdjustCount() + 1);
      } else {
        ap.setAdjustCount(ap.getAdjustCount() - 1);
      }
    }

    ap.setBenchmarkEarningsYield(apr.benchmarkEarningsYield());
    ap.setPortfolioEarningsYield(apr.portfolioEarningsYield());
    ap.setAlpha(apr.alpha());

    return ap;
  }
}
