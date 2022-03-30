/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentInfo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface AdjustmentInfoRepository
    extends JpaRepository<AdjustmentInfo, Long>, JpaSpecificationExecutor<AdjustmentInfo> {

  List<AdjustmentInfo> findByAdjustmentRecordId(Long adjustmentRecordId);

  // TODO:
  // 1. sort by portfolio_adjustment_record's adjust_date desc, adjust_version
  // desc, and is_adjusted = true
  // 2. take sorted portfolio_adjustment_record's id and query
  // portfolio_adjustment_info
  final String queryDescSort = """
      SELECT ai
      FROM AdjustmentInfo ai
      """;

  @Query(queryDescSort)
  List<AdjustmentInfo> findAllDescSort(Pageable pageable);

  void deleteByAdjustmentRecordId(Long adjustmentRecordId);

  final String deleteAllRecordsByPactId = """
      DELETE AdjustmentInfo ai WHERE ai.adjustmentRecord.id in :arIds
      """;

  @Modifying
  @Query(deleteAllRecordsByPactId)
  void deleteAllRecordsByPactId(@Param("arIds") List<Long> arIds);
}
