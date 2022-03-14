/**
 * Created by Jacob Xie on 3/11/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.ConstituentRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PerformanceRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper.PortfolioCalculationHelper;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper.PortfolioCalculationHelper.ConstituentsResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstituentService {

  @Autowired
  private ConstituentRepository cRepo;

  @Autowired
  private PerformanceRepository pRepo;

  public List<Constituent> getConstituentsByAdjustmentRecordId(int adjustmentRecordId) {
    return cRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public List<Constituent> getConstituentsByAdjustmentRecordIds(List<Integer> adjustmentRecordIds) {
    return cRepo.findByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

  public Optional<Constituent> getConstituentById(int id) {
    return cRepo.findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public Constituent createConstituent(Constituent constituent) {
    // 0. validate constituent
    // IMPORTANT: constituent's adjustmentRecord id cannot be null. In other words,
    // it must have an adjustmentRecord to create a constituent.
    int adjustmentRecordId = constituent.get_adjustment_record_id();

    // 1. save constituent.
    Constituent newC = cRepo.save(constituent);

    // 2. find all constituents in the portfolio
    List<Constituent> constituents = getConstituentsByAdjustmentRecordId(adjustmentRecordId);

    // 3. recalculate all constituents and their related performance
    ConstituentsResult res = PortfolioCalculationHelper.calculatePortfolioEarningsYield(constituents);

    // 4. update all constituents' dynamic weight
    cRepo.saveAll(res.constituents());

    // 5. update or create performance
    Performance performance = pRepo
        .findByAdjustmentRecordId(adjustmentRecordId)
        .orElse(new Performance());
    performance.setPortfolioEarningsYield(res.earningsYield());
    performance.setAlpha(res.earningsYield() - performance.getBenchmarkEarningsYield());
    pRepo.save(performance);

    return cRepo
        .findById(newC.getId())
        .orElseThrow(() -> new RuntimeException(String.format("Constituent %d not found", newC.getId())));
  }

  @Transactional(rollbackFor = Exception.class)
  public Constituent updateConstituent(int id, Constituent constituent) {
    // 0. validate constituent
    int adjustmentRecordId = constituent.get_adjustment_record_id();

    // 1. update constituent
    cRepo.findById(id).map(
        record -> {
          record.setAdjustDate(constituent.getAdjustDate());
          record.setSymbol(constituent.getSymbol());
          record.setAbbreviation(constituent.getAbbreviation());
          record.setAdjustDatePrice(constituent.getAdjustDatePrice());
          record.setCurrentPrice(constituent.getCurrentPrice());
          record.setAdjustDateFactor(constituent.getAdjustDateFactor());
          record.setCurrentFactor(constituent.getCurrentFactor());
          record.setStaticWeight(constituent.getStaticWeight());
          record.setPbpe(constituent.getPbpe());
          record.setMarketValue(constituent.getMarketValue());
          record.setEarningsYield(constituent.getEarningsYield());
          return cRepo.save(record);
        }).orElseThrow(() -> new RuntimeException(String.format("Constituent %d not found", id)));

    // 2. find all constituents in the portfolio
    List<Constituent> constituents = getConstituentsByAdjustmentRecordId(adjustmentRecordId);

    // 3. recalculate all constituents and their related performance
    ConstituentsResult res = PortfolioCalculationHelper.calculatePortfolioEarningsYield(constituents);

    // 4. update all constituents' dynamic weight
    cRepo.saveAll(res.constituents());

    // 5. update or create performance
    Performance performance = pRepo
        .findByAdjustmentRecordId(adjustmentRecordId)
        .orElse(new Performance());
    performance.setPortfolioEarningsYield(res.earningsYield());
    performance.setAlpha(res.earningsYield() - performance.getBenchmarkEarningsYield());
    pRepo.save(performance);

    return cRepo
        .findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("Constituent %d not found", id)));

  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteConstituent(int id) {

    // 0. find constituent and get adjustmentRecordId
    Constituent c = cRepo.findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("Constituent %d not found", id)));
    int adjustmentRecordId = c.get_adjustment_record_id();

    // 1. delete constituent. Since we've called findById, here can be sure that the
    // constituent is deleted.
    cRepo.deleteById(id);

    // 2. find all constituents in the portfolio
    List<Constituent> constituents = getConstituentsByAdjustmentRecordId(adjustmentRecordId);

    // 3. recalculate all constituents and their related performance
    ConstituentsResult res = PortfolioCalculationHelper.calculatePortfolioEarningsYield(constituents);

    // 4. update all constituents' dynamic weight
    cRepo.saveAll(res.constituents());

    // 5. update or create performance
    Performance performance = pRepo
        .findByAdjustmentRecordId(adjustmentRecordId)
        .orElse(new Performance());
    performance.setPortfolioEarningsYield(res.earningsYield());
    performance.setAlpha(res.earningsYield() - performance.getBenchmarkEarningsYield());
    pRepo.save(performance);
  }

  // TODO:
  // remove related performance
  public void deleteConstituentsByAdjustmentRecordId(int adjustmentRecordId) {
    cRepo.deleteByAdjustmentRecordId(adjustmentRecordId);
  }

  // TODO:
  // same as above
  public void deleteConstituentsByAdjustmentRecordIds(List<Integer> adjustmentRecordIds) {
    cRepo.deleteByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

}
