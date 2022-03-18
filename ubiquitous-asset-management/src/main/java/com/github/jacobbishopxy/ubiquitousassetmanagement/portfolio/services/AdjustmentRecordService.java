/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.FullAdjustmentRecord;
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

  public List<AdjustmentRecord> getARByPactId(int pactId) {
    return arRepo.findByPactId(pactId);
  }

  public List<AdjustmentRecord> getARAtLatestAdjustDate(int pactId) {
    return arRepo.findByPactIdAndLatestAdjustDate(pactId);
  }

  // A powerful query.
  // Get all adjustment records at the latest date and latest version.
  public List<AdjustmentRecord> getARsAtLatestAdjustDateVersion(List<Integer> pactIds) {
    return arRepo.findByPactIdsAndLatestAdjustDateVersion(pactIds);
  }

  public Optional<AdjustmentRecord> getARAtLatestAdjustDateAndVersion(int pactId) {
    return arRepo
        .findByPactIdAndLatestAdjustDate(pactId)
        .stream()
        .findFirst();
  }

  // Get the latest date's latest version AdjustmentRecord with `Pact`
  // explicitly included.
  public Optional<FullAdjustmentRecord> getFullARAtLatestAdjustDateAndVersion(int pactId) {
    return arRepo
        .findByPactIdAndLatestAdjustDate(pactId)
        .stream()
        .findFirst()
        .map(ar -> {
          return FullAdjustmentRecord.fromAdjustmentRecord(ar);
        });
  }

  public Optional<AdjustmentRecord> getARById(int id) {
    return arRepo.findById(id);
  }

  // =======================================================================
  // Mutation methods
  //
  // called by internal services
  // =======================================================================

  public AdjustmentRecord createPAR(AdjustmentRecord par) {
    return arRepo.save(par);
  }

  public Optional<AdjustmentRecord> updatePAR(int id, AdjustmentRecord par) {
    return arRepo.findById(id).map(
        record -> {
          record.setPact(par.getPact());
          record.setAdjustDate(par.getAdjustDate());
          record.setAdjustVersion(par.getAdjustVersion());
          return arRepo.save(record);
        });
  }

  public void deletePAR(int id) {
    arRepo.deleteById(id);
  }
}
