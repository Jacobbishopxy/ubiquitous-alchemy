/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.util.Date;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioPact;

public record PortfolioPactOutput(String alias,
    String promoter,
    String industry,
    Date startDate,
    Date endDate,
    String description,
    Boolean isActive,
    Date lastUpdatedDate) {

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
        portfolioPact.getLastUpdatedDate());
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
        portfolioPact.getLastUpdatedDate());
  }
}
