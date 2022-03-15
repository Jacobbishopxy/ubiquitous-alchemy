/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentInfoRepository extends JpaRepository<AdjustmentInfo, Integer> {

  List<AdjustmentInfo> findByAdjustmentRecordId(int adjustmentRecordId);

  void deleteByAdjustmentRecordId(int adjustmentRecordId);
}
