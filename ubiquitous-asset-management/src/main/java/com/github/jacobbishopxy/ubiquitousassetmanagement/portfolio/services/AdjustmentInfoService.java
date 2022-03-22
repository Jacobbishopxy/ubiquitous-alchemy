/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.AdjustmentInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AdjustmentInfoService
 */
@Service
public class AdjustmentInfoService {

  @Autowired
  private AdjustmentInfoRepository aRepo;

  // =======================================================================
  // Query methods
  //
  // expose to controller
  // =======================================================================

  public List<AdjustmentInfo> getAdjustmentInfosByAdjustmentRecordId(Long adjustmentRecordId) {
    return aRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public Optional<AdjustmentInfo> getAdjustmentInfoById(Long id) {
    return aRepo.findById(id);
  }

  // =======================================================================
  // Mutation methods
  //
  // called by internal services
  // =======================================================================

  public AdjustmentInfo createAdjustmentInfo(AdjustmentInfo adjustmentInfo) {
    return aRepo.save(adjustmentInfo);
  }

  public List<AdjustmentInfo> createAdjustmentInfos(List<AdjustmentInfo> adjustmentInfos) {
    return aRepo.saveAll(adjustmentInfos);
  }

  public Optional<AdjustmentInfo> updateAdjustmentInfo(Long id, AdjustmentInfo adjustmentInfo) {
    return aRepo.findById(id).map(a -> {
      a.setAdjustTime(adjustmentInfo.getAdjustTime());
      a.setSymbol(adjustmentInfo.getSymbol());
      a.setAbbreviation(adjustmentInfo.getAbbreviation());
      a.setOperation(adjustmentInfo.getOperation());
      a.setStaticWeight(adjustmentInfo.getStaticWeight());
      a.setDescription(adjustmentInfo.getDescription());
      return aRepo.save(a);
    });
  }

  // EXCEPTION: expose to controller
  public Optional<AdjustmentInfo> modifyAdjustmentInfo(Long id, LocalTime adjustTime, String description) {
    return aRepo.findById(id).map(a -> {
      if (adjustTime != null) {
        a.setAdjustTime(adjustTime);
      }
      if (description != null) {
        a.setDescription(description);
      }

      return aRepo.save(a);
    });
  }

  public void deleteAdjustmentInfo(Long id) {
    aRepo.deleteById(id);
  }
}
