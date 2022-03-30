/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface AdjustmentInfoRepository extends JpaRepository<AdjustmentInfo, Long> {

  List<AdjustmentInfo> findByAdjustmentRecordId(Long adjustmentRecordId);

  void deleteByAdjustmentRecordId(Long adjustmentRecordId);

  final String deleteAllRecordsByPactId = """
      DELETE AdjustmentInfo ai WHERE ai.adjustmentRecord.id in :arIds
      """;

  @Modifying
  @Query(deleteAllRecordsByPactId)
  void deleteAllRecordsByPactId(@Param("arIds") List<Long> arIds);
}
