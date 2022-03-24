/**
 * Created by Jacob Xie on 3/7/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.AdjustmentInfoRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.AdjustmentRecordRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.BenchmarkRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.ConstituentRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PactRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PerformanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PactService {

  @Autowired
  private PactRepository pRepo;

  @Autowired
  private AdjustmentRecordRepository arRepo;

  @Autowired
  private AdjustmentInfoRepository aiRepo;

  @Autowired
  private ConstituentRepository cRepo;

  @Autowired
  private BenchmarkRepository bRepo;

  @Autowired
  private PerformanceRepository perfRepo;

  // =======================================================================
  // Query methods
  // =======================================================================

  public List<Pact> getAllPacts(Boolean isActive) {
    if (isActive == null) {
      return pRepo.findAll();
    } else {
      return pRepo.findByIsActive(isActive);
    }
  }

  public Optional<Pact> getPactById(Long id) {
    return pRepo.findById(id);
  }

  public Optional<Pact> getPactByAlias(String alias) {
    return pRepo.findByAlias(alias);
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @Transactional(rollbackFor = Exception.class)
  public Pact createPact(Pact portfolioPact) {
    // if alias is not set, use promoter_nickname and start_date as alias
    if (portfolioPact.getAlias() == null) {
      String alias = portfolioPact.getIndustryInfo().getName() + "_" +
          portfolioPact.getPromoter().getNickname() + "_" +
          portfolioPact.getStartDate().toString();
      portfolioPact.setAlias(alias);
    }
    // save new pact
    Pact pact = pRepo.save(portfolioPact);
    // auto create adjustment record. notice that the adjust date and adjust version
    // are not set, this means the portfolio is under an unsettled status.
    AdjustmentRecord adjustmentRecord = new AdjustmentRecord();
    adjustmentRecord.setPact(pact);
    adjustmentRecord.setIsUnsettled(true);
    adjustmentRecord = arRepo.save(adjustmentRecord);
    // auto create performance, all other fields are null
    Performance performance = new Performance();
    performance.setAdjustmentRecord(adjustmentRecord);
    perfRepo.save(performance);

    return pact;
  }

  public Optional<Pact> updatePact(Long id, Pact portfolioPact) {
    return pRepo.findById(id).map(
        record -> {
          record.setAlias(portfolioPact.getAlias());
          record.setPromoter(portfolioPact.getPromoter());
          record.setIndustryInfo(portfolioPact.getIndustryInfo());
          record.setStartDate(portfolioPact.getStartDate());
          record.setEndDate(portfolioPact.getEndDate());
          record.setDescription(portfolioPact.getDescription());
          record.setIsActive(portfolioPact.getIsActive());
          return pRepo.save(record);
        });
  }

  public Optional<Pact> updatePact(String alias, Pact portfolioPact) {
    return pRepo.findByAlias(alias).map(
        record -> {
          record.setAlias(portfolioPact.getAlias());
          record.setPromoter(portfolioPact.getPromoter());
          record.setIndustryInfo(portfolioPact.getIndustryInfo());
          record.setStartDate(portfolioPact.getStartDate());
          record.setEndDate(portfolioPact.getEndDate());
          record.setDescription(portfolioPact.getDescription());
          record.setIsActive(portfolioPact.getIsActive());
          return pRepo.save(record);
        });
  }

  /**
   * Delete a pact.
   *
   * IMPORTANT: Be very careful when deleting a pact, because it will also delete
   * all adjustment records and their related
   * constituents/benchmarks/performances.
   *
   * @param id
   */
  @Transactional(rollbackFor = Exception.class)
  public void deletePact(Long id) {

    // find all related adjustment records ids
    List<Long> arIds = arRepo.findAllRecordIdsByPactId(id);

    // delete all adjustment infos
    aiRepo.deleteAllRecordsByPactId(arIds);

    // delete all performances
    perfRepo.deleteAllRecordsByARIds(arIds);

    // delete all constituents
    cRepo.deleteAllRecordsByARIds(arIds);

    // delete all benchmarks
    bRepo.deleteAllRecordsByARIds(arIds);

    // delete all adjustment records
    arRepo.deleteAllRecordsByPactId(id);

    // delete the pact
    pRepo.deleteById(id);
  }

  public void deletePact(String alias) {
    pRepo.deleteByAlias(alias);
  }

}
