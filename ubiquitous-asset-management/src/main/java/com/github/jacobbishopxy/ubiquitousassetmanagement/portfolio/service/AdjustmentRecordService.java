/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AdjustmentRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * AdjustmentRecordService
 *
 * We shall only expose GET methods to controller, since any other mutations are
 * called by internal services.
 */
@Service
public class AdjustmentRecordService {

  @Autowired
  private AdjustmentRecordRepository arRepo;

  // =======================================================================
  // Query methods
  //
  // expose to controller
  // =======================================================================

  public List<AdjustmentRecord> getARLatestAdjustDate(Long pactId) {
    return arRepo.findByPactIdAndLatestAdjustDate(pactId);
  }

  public Optional<AdjustmentRecord> getUnsettledAR(Long pactId) {
    return arRepo.findUnsettledByPactId(pactId);
  }

  public List<AdjustmentRecord> getARSortDesc(Long pactId, Pageable pageable) {
    return arRepo.findByPactIdDescSort(pactId, pageable);
  }

  public List<AdjustmentRecord> getARSortDescAndIsAdjustedTrue(Long pactId, Pageable pageable) {
    return arRepo.findByPactIdDescSortAndIsAdjustedTrue(pactId, pageable);
  }

  public List<AdjustmentRecord> getUnsettledARs(List<Long> pactIds) {
    return arRepo.findUnsettledByPactIds(pactIds);
  }

  // A powerful query.
  // Get all adjustment records at the latest date's latest version.
  public List<AdjustmentRecord> getLatestSettledARs(List<Long> pactIds) {
    return arRepo.findByPactIdsAndLatestAdjustDateVersion(pactIds);
  }

  public Optional<AdjustmentRecord> getLatestSettledAR(Long pactId) {
    return arRepo
        .findByPactIdAndLatestAdjustDate(pactId)
        .stream()
        .findFirst();
  }

  public Optional<AdjustmentRecord> getARById(Long id) {
    return arRepo.findById(id);
  }

  public Optional<AdjustmentRecord> getFullARById(Long id) {
    return arRepo.findById(id);
  }

  // =======================================================================
  // Mutation methods
  //
  // called by internal services
  // =======================================================================

  public AdjustmentRecord createAR(AdjustmentRecord adjustmentRecord) {
    return arRepo.save(adjustmentRecord);
  }

  public Optional<AdjustmentRecord> updateAR(Long id, AdjustmentRecord adjustmentRecord) {
    return arRepo.findById(id).map(
        record -> {
          record.setPact(adjustmentRecord.getPact());
          record.setAdjustDate(adjustmentRecord.getAdjustDate());
          record.setAdjustVersion(adjustmentRecord.getAdjustVersion());
          return arRepo.save(record);
        });
  }

  public void deleteAR(Long id) {
    arRepo.deleteById(id);
  }
}
