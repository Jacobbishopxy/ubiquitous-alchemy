/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.AdjustmentRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

  public List<AdjustmentRecord> getARAtLatestAdjustDate(Long pactId) {
    return arRepo.findByPactIdAndLatestAdjustDate(pactId);
  }

  public List<AdjustmentRecord> getARSortDesc(Long pactId) {
    return arRepo.findByPactIdDescSort(pactId);
  }

  // A powerful query.
  // Get all adjustment records at the latest date and latest version.
  public List<AdjustmentRecord> getARsAtLatestAdjustDateVersion(List<Long> pactIds) {
    return arRepo.findByPactIdsAndLatestAdjustDateVersion(pactIds);
  }

  public Optional<AdjustmentRecord> getARAtLatestAdjustDateAndVersion(Long pactId) {
    return arRepo
        .findByPactIdAndLatestAdjustDate(pactId)
        .stream()
        .findFirst();
  }

  // Get the latest date's latest version AdjustmentRecord with `Pact`
  // explicitly included.
  public Optional<AdjustmentRecord> getFullARAtLatestAdjustDateAndVersion(Long pactId) {
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
