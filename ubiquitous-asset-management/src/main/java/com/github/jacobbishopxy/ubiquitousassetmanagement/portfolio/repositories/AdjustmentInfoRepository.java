/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentInfoRepository extends JpaRepository<AdjustmentInfo, Long> {

  List<AdjustmentInfo> findByAdjustmentRecordId(Long adjustmentRecordId);

  void deleteByAdjustmentRecordId(Long adjustmentRecordId);
}
