/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Performance;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

  Optional<Performance> findByAdjustmentRecordId(Long adjustmentRecordId);

  void deleteByAdjustmentRecordId(Long adjustmentRecordId);

  List<Performance> findByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);

  void deleteByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);

  final String deleteAllRecordsByARIds = """
      DELETE Performance p WHERE p.adjustmentRecord.id in :arIds
      """;

  @Modifying
  @Query(deleteAllRecordsByARIds)
  void deleteAllRecordsByARIds(@Param("arIds") List<Long> arIds);
}
