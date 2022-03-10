/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioAdjustmentRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PortfolioAdjustmentRecordRepository
    extends JpaRepository<PortfolioAdjustmentRecord, Integer>, JpaSpecificationExecutor<PortfolioAdjustmentRecord> {

  List<PortfolioAdjustmentRecord> findByPortfolioPactId(int portfolioPactId);

  @Query("select p from PortfolioAdjustmentRecord p where p.portfolioPact.id = ?1 and p.adjustDate = (select max(p2.adjustDate) from PortfolioAdjustmentRecord p2 where p2.portfolioPact.id = ?1)")
  List<PortfolioAdjustmentRecord> findByPortfolioPactIdAndLatestAdjustDate(int portfolioPactId);

}
