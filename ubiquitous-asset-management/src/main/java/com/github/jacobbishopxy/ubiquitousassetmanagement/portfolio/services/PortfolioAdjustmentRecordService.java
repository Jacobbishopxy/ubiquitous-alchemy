/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioAdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PortfolioAdjustmentRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioAdjustmentRecordService {

  @Autowired
  private PortfolioAdjustmentRecordRepository parRepo;

  public List<PortfolioAdjustmentRecord> getPortfolioAdjustmentRecord(int portfolioPactId) {
    return parRepo.findByPortfolioPactId(portfolioPactId);
  }

  public List<PortfolioAdjustmentRecord> getPortfolioAdjustmentRecordAtLatestAdjustDate(int portfolioPactId) {
    return parRepo.findByPortfolioPactIdAndLatestAdjustDate(portfolioPactId);
  }
}
