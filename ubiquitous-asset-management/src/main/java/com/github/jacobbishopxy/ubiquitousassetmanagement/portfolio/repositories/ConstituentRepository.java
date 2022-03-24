/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ConstituentRepository
    extends JpaRepository<Constituent, Long>, JpaSpecificationExecutor<Constituent> {

  List<Constituent> findByAdjustmentRecordId(Long adjustmentRecordId);

  void deleteByAdjustmentRecordId(Long adjustmentRecordId);

  List<Constituent> findByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);

  void deleteByAdjustmentRecordIdIn(List<Long> adjustmentRecordIds);

  final String deleteAllRecordsByARIds = """
      DELETE Constituent c WHERE c.adjustmentRecord.id in :arIds
      """;

  @Modifying
  @Query(deleteAllRecordsByARIds)
  void deleteAllRecordsByARIds(@Param("arIds") List<Long> arIds);
}
