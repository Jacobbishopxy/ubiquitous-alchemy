/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Schema(name = "PortfolioPactOutput", description = "Pact output")
public record PactOutput(String alias,
    String promoter,
    String industry,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate startDate,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate endDate,
    String description,
    Boolean isActive) {

  public static PactOutput fromPortfolioPact(
      Pact portfolioPact) {
    return new PactOutput(
        portfolioPact.getAlias(),
        portfolioPact.getPromoter().getNickname(),
        portfolioPact.getIndustryInfo().getName(),
        portfolioPact.getStartDate(),
        portfolioPact.getEndDate(),
        portfolioPact.getDescription(),
        portfolioPact.getIsActive());
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
        portfolioPact.getIsActive());
  }
}
