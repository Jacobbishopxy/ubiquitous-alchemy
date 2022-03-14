/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {

  Optional<Performance> findByAdjustmentRecordId(int adjustmentRecordId);

  void deleteByAdjustmentRecordId(int adjustmentRecordId);

  List<Performance> findByAdjustmentRecordIdIn(List<Integer> adjustmentRecordIds);

  void deleteByAdjustmentRecordIdIn(List<Integer> adjustmentRecordIds);
}
