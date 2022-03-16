/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AdjustmentRecordRepository
    extends JpaRepository<AdjustmentRecord, Integer>, JpaSpecificationExecutor<AdjustmentRecord> {

  List<AdjustmentRecord> findByPactId(int portfolioPactId);

  String queryLatestAdjustDate = """
      SELECT p1 FROM AdjustmentRecord p1
      WHERE p1.pact.id = ?1
      AND p1.adjustDate = (SELECT MAX(p2.adjustDate) FROM AdjustmentRecord p2 WHERE p2.pact.id = ?1)
      ORDER BY p1.adjustVersion DESC
      """;

  @Query(queryLatestAdjustDate)
  List<AdjustmentRecord> findByPactIdAndLatestAdjustDate(int portfolioPactId);
}
