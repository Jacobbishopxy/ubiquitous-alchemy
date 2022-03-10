/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioAdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioPact;

public record PortfolioPactOutput(String alias,
    String promoter,
    String industry,
    LocalDate startDate,
    LocalDate endDate,
    String description,
    Boolean isActive,
    List<PortfolioAdjustmentRecord> portfolioAdjustmentRecords) {

  public static PortfolioPactOutput fromPortfolioPact(
      PortfolioPact portfolioPact) {
    return new PortfolioPactOutput(
        portfolioPact.getAlias(),
        portfolioPact.getPromoter().getNickname(),
        portfolioPact.getIndustryInfo().getName(),
        portfolioPact.getStartDate(),
        portfolioPact.getEndDate(),
        portfolioPact.getDescription(),
        portfolioPact.getIsActive(),
        portfolioPact.getPortfolioAdjustmentRecords());
  }

  public static PortfolioPactOutput fromPortfolioPact(
      PortfolioPact portfolioPact,
      String promoterName,
      String industryName) {
    return new PortfolioPactOutput(
        portfolioPact.getAlias(),
        promoterName,
        industryName,
        portfolioPact.getStartDate(),
        portfolioPact.getEndDate(),
        portfolioPact.getDescription(),
        portfolioPact.getIsActive(),
        portfolioPact.getPortfolioAdjustmentRecords());
  }
}
