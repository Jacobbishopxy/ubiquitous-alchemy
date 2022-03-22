/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

  Optional<Performance> findByAdjustmentRecordId(Long adjustmentRecordId);

  void deleteByAdjustmentRecordId(Long adjustmentRecordId);

  List<Performance> findByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);

  void deleteByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);
}
