/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface BenchmarkRepository
    extends JpaRepository<Benchmark, Long>, JpaSpecificationExecutor<Benchmark> {

  List<Benchmark> findByAdjustmentRecordId(Long adjustmentRecordId);

  void deleteByAdjustmentRecordId(Long adjustmentRecordId);

  List<Benchmark> findByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);

  void deleteByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);

  final String deleteAllRecordsByPactId = """
      DELETE Benchmark b WHERE b.adjustmentRecord.id in :arIds
      """;

  @Modifying
  @Query(deleteAllRecordsByPactId)
  void deleteAllRecordsByARIds(@Param("arIds") List<Long> arIds);
}
