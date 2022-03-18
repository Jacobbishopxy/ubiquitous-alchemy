/**
 * Created by Jacob Xie on 3/18/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.IndustryInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;

public record SimplePact(
    Integer id,
    String alias,
    Promoter promoter,
    IndustryInfo industryInfo,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate startDate,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate endDate,
    String description,
    Boolean isActive) {

  public static SimplePact fromPact(Pact pact) {
    return new SimplePact(
        pact.getId(),
        pact.getAlias(),
        pact.getPromoter(),
        pact.getIndustryInfo(),
        pact.getStartDate(),
        pact.getEndDate(),
        pact.getDescription(),
        pact.getIsActive());
  }

}
