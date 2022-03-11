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

  @Query("select p1 from AdjustmentRecord p1 where p1.pact.id = ?1 and p1.adjustDate = (select max(p2.adjustDate) from AdjustmentRecord p2 where p2.pact.id = ?1)")
  List<AdjustmentRecord> findByPactIdAndLatestAdjustDate(int portfolioPactId);

}
