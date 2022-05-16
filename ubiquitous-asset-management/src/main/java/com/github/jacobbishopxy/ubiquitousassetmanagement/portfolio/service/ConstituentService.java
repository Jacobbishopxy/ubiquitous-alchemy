/**
 * Created by Jacob Xie on 3/11/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AccumulatedPerformance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.ConstituentUpdate;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AccumulatedPerformanceRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.ConstituentRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.PerformanceRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.helper.PortfolioCalculationHelper;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.helper.PortfolioCalculationHelper.ConstituentsResult;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstituentService {

  @Autowired
  private ConstituentRepository cRepo;

  @Autowired
  private PerformanceRepository pRepo;

  @Autowired
  private AccumulatedPerformanceRepository apRepo;

  @Autowired
  private RecalculateService recalculateService;

  // =======================================================================
  // Query methods
  //
  // expose to controller
  // =======================================================================

  public List<Constituent> getConstituentsByAdjustmentRecordId(Long adjustmentRecordId) {
    return cRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public List<Constituent> getConstituentsByAdjustmentRecordIds(List<Long> adjustmentRecordIds) {
    return cRepo.findByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

  public Optional<Constituent> getConstituentById(Long id) {
    return cRepo.findById(id);
  }

  // =======================================================================
  // Mutation methods
  //
  // called by internal services
  // =======================================================================

  // raw mutation is called when create/update/delete a constituent
  @Transactional(rollbackFor = Exception.class)
  private void rawMutation(List<Constituent> constituents) {
    // 0. constituents cannot be empty
    if (constituents.isEmpty()) {
      throw new IllegalArgumentException("Constituents cannot be empty");
    }

    // 1. get adjustment record id
    AdjustmentRecord adjustmentRecord = constituents.get(0).getAdjustmentRecord();
    Long adjustmentRecordId = adjustmentRecord.getId();
    Pact pact = adjustmentRecord.getPact();

    // 2. recalculate all constituents and their related performance,
    // validate modified constituents
    ConstituentsResult res = PortfolioCalculationHelper
        .modifyConstituentsAndCalculatePortfolioEarningsYield(constituents);

    // 3. update all constituents' dynamic weight
    cRepo.saveAll(res.constituents());

    // 4. update or create performance
    Performance performance = pRepo
        .findByAdjustmentRecordId(adjustmentRecordId)
        .orElse(new Performance());
    performance.setAdjustmentRecord(new AdjustmentRecord(adjustmentRecordId));
    performance.setPortfolioEarningsYield(res.earningsYield());
    if (res.earningsYield() != null && performance.getBenchmarkEarningsYield() != null) {
      performance.setAlpha(res.earningsYield() - performance.getBenchmarkEarningsYield());
    }
    pRepo.save(performance);

    // 5. update or create accumulated performance
    AccumulatedPerformance ap = recalculateService.recalculateAccumulatedPerformance(pact, false, false);
    apRepo.save(ap);
  }

  // common mutations is a wrapper of raw mutation, which only needs adjustment
  // record id as input
  @Transactional(rollbackFor = Exception.class)
  private void commonMutation(Long adjustmentRecordId) {
    // find all constituents in the portfolio
    List<Constituent> constituents = getConstituentsByAdjustmentRecordId(adjustmentRecordId);

    rawMutation(constituents);
  }

  @Transactional(rollbackFor = Exception.class)
  public Constituent createConstituent(Constituent constituent) {
    // 0. validate constituent
    // IMPORTANT: constituent's adjustmentRecord id cannot be null. In other words,
    // it must have an adjustmentRecord to create a constituent.
    Long adjustmentRecordId = constituent.getAdjRecordId();

    // 1. save constituent.
    Constituent newC = cRepo.save(constituent);

    // 2. common mutation
    commonMutation(adjustmentRecordId);

    return cRepo
        .findById(newC.getId())
        .orElseThrow(() -> new RuntimeException(
            String.format("Constituent %d not found", newC.getId())));
  }

  @Transactional(rollbackFor = Exception.class)
  public List<Constituent> createConstituents(List<Constituent> constituents) {
    // 0. validate constituents
    // IMPORTANT: constituents' adjustmentRecord id cannot be null. In other words,
    // they must have an adjustmentRecord to create constituents.
    List<Long> adjustmentRecordIds = constituents.stream()
        .map(Constituent::getAdjRecordId)
        .collect(Collectors.toList());
    Set<Long> uniqueARIds = Sets.newHashSet(adjustmentRecordIds);
    if (uniqueARIds.size() != 1) {
      throw new IllegalArgumentException("Constituents must have the same adjustmentRecord id");
    }

    // 1. save constituents.
    List<Constituent> newCs = cRepo.saveAll(constituents);

    // 2. raw mutation
    rawMutation(newCs);

    return newCs;
  }

  @Transactional(rollbackFor = Exception.class)
  public Optional<Constituent> updateConstituent(Long id, Constituent constituent) {
    // 0. validate constituent
    Long adjustmentRecordId = constituent.getAdjRecordId();

    // 1. update constituent
    cRepo
        .findById(id)
        .map(
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
            })
        .orElseThrow(() -> new RuntimeException(
            String.format("Constituent %d not found", id)));

    // 2. common mutation
    commonMutation(adjustmentRecordId);

    return cRepo.findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public List<Constituent> updateConstituents(List<Constituent> constituents) {
    // 0. make sure all constituents' id are valid
    List<Long> adjustmentRecordIds = constituents
        .stream()
        .map(Constituent::getAdjRecordId)
        .collect(Collectors.toList());
    Set<Long> uniqueARIds = Sets.newHashSet(adjustmentRecordIds);
    if (uniqueARIds.size() != 1) {
      throw new IllegalArgumentException("All constituents must have the same adjustmentRecord id");
    }

    // 1. only modify constituents that are in the database
    List<Long> ids = constituents
        .stream()
        .map(Constituent::getId)
        .collect(Collectors.toList());

    List<Constituent> newCons = cRepo
        .findAllById(ids)
        .stream()
        .map(c -> {
          int idx = ids.indexOf(c.getId());
          c.setAdjustDate(constituents.get(idx).getAdjustDate());
          c.setSymbol(constituents.get(idx).getSymbol());
          c.setAbbreviation(constituents.get(idx).getAbbreviation());
          c.setAdjustDatePrice(constituents.get(idx).getAdjustDatePrice());
          c.setCurrentPrice(constituents.get(idx).getCurrentPrice());
          c.setAdjustDateFactor(constituents.get(idx).getAdjustDateFactor());
          c.setCurrentFactor(constituents.get(idx).getCurrentFactor());
          c.setStaticWeight(constituents.get(idx).getStaticWeight());
          c.setPbpe(constituents.get(idx).getPbpe());
          c.setMarketValue(constituents.get(idx).getMarketValue());
          c.setEarningsYield(constituents.get(idx).getEarningsYield());
          return c;
        })
        .collect(Collectors.toList());

    // 2. raw mutation
    rawMutation(newCons);

    return newCons;
  }

  public Optional<Constituent> modifyConstituent(Long id, ConstituentUpdate dto) {
    return cRepo
        .findById(id)
        .map(c -> {
          if (dto.currentFactor() != null) {
            c.setCurrentFactor(dto.currentFactor());
          }
          if (dto.currentPrice() != null) {
            c.setCurrentPrice(dto.currentPrice());
          }
          if (dto.pbpe() != null) {
            c.setPbpe(dto.pbpe());
          }
          if (dto.marketValue() != null) {
            c.setMarketValue(dto.marketValue());
          }

          return cRepo.save(c);
        });

  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteConstituent(Long id) {
    // 0. find constituent and get adjustmentRecordId
    Constituent c = cRepo
        .findById(id)
        .orElseThrow(() -> new RuntimeException(
            String.format("Constituent %d not found", id)));
    Long adjustmentRecordId = c.getAdjRecordId();

    // 1. delete constituent. Since we've called findById, here can be sure that the
    // constituent is deleted.
    cRepo.deleteById(id);

    // 2. common mutation
    commonMutation(adjustmentRecordId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteConstituents(List<Long> ids) {
    // 0. find constituents and get adjustmentRecordId
    List<Constituent> cs = cRepo.findAllById(ids);
    List<Long> adjustmentRecordIds = cs
        .stream()
        .map(Constituent::getAdjRecordId)
        .collect(Collectors.toList());
    Set<Long> uniqueARIds = Sets.newHashSet(adjustmentRecordIds);
    if (uniqueARIds.size() != 1) {
      throw new IllegalArgumentException("All constituents must have the same adjustmentRecord id");
    }

    // 1. delete constituents. Since we've called findAllById, here can be sure that
    // the
    // constituents are deleted.
    cRepo.deleteAll(cs);

    // 2. common mutation
    commonMutation(adjustmentRecordIds.get(0));
  }

  // =======================================================================
  // DANGEROUS! Mutation methods
  //
  // Should only used in development environment
  // =======================================================================

  // delete all constituents in the portfolio.
  // IMPORTANT: adjustRecord is not deleted
  @Transactional(rollbackFor = Exception.class)
  public void deleteConstituentsByAdjustmentRecordId(Long adjustmentRecordId) {
    cRepo.deleteByAdjustmentRecordId(adjustmentRecordId);

    pRepo.deleteByAdjustmentRecordId(adjustmentRecordId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteConstituentsByAdjustmentRecordIds(List<Long> adjustmentRecordIds) {
    cRepo.deleteByAdjustmentRecordIdIn(adjustmentRecordIds);

    pRepo.deleteByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

}
