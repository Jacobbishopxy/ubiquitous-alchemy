/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;

public record PactOutput(String alias,
    String promoter,
    String industry,
    LocalDate startDate,
    LocalDate endDate,
    String description,
    Boolean isActive,
    List<AdjustmentRecord> adjustmentRecords) {

  public static PactOutput fromPortfolioPact(
      Pact portfolioPact) {
    return new PactOutput(
        portfolioPact.getAlias(),
        portfolioPact.getPromoter().getNickname(),
        portfolioPact.getIndustryInfo().getName(),
        portfolioPact.getStartDate(),
        portfolioPact.getEndDate(),
        portfolioPact.getDescription(),
        portfolioPact.getIsActive(),
        portfolioPact.getAdjustmentRecords());
  }

  public static PactOutput fromPortfolioPact(
      Pact portfolioPact,
      String promoterName,
      String industryName) {
    return new PactOutput(
        portfolioPact.getAlias(),
        promoterName,
        industryName,
        portfolioPact.getStartDate(),
        portfolioPact.getEndDate(),
        portfolioPact.getDescription(),
        portfolioPact.getIsActive(),
        portfolioPact.getAdjustmentRecords());
  }
}
