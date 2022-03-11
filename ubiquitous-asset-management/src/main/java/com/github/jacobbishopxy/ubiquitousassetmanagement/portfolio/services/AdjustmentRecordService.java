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
  private AdjustmentRecordRepository parRepo;

  // expose to controller
  public List<AdjustmentRecord> getPAR(int portfolioPactId) {
    return parRepo.findByPactId(portfolioPactId);
  }

  // expose to controller
  public List<AdjustmentRecord> getPARAtLatestAdjustDate(int portfolioPactId) {
    return parRepo.findByPactIdAndLatestAdjustDate(portfolioPactId);
  }

  // expose to controller
  public Optional<AdjustmentRecord> getPARAtLatestAdjustDateAndVersion(int portfolioPactId) {
    return parRepo.findByPactIdAndLatestAdjustDate(portfolioPactId).stream().findFirst();
  }

  public AdjustmentRecord createPAR(AdjustmentRecord par) {
    return parRepo.save(par);
  }

  public Optional<AdjustmentRecord> updatePAR(int id, AdjustmentRecord par) {
    return parRepo.findById(id).map(
        record -> {
          record.setPact(par.getPact());
          record.setAdjustDate(par.getAdjustDate());
          record.setAdjustVersion(par.getAdjustVersion());
          return parRepo.save(record);
        });
  }

  public void deletePAR(int id) {
    parRepo.deleteById(id);
  }
}
