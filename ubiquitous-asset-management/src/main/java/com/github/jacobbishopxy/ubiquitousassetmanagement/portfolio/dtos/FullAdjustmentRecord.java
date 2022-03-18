/**
 * Created by Jacob Xie on 3/18/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;

public record FullAdjustmentRecord(
    Integer id,
    SimplePact pact,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate adjustDate,
    Integer adjustVersion) {

  public static FullAdjustmentRecord fromAdjustmentRecord(AdjustmentRecord ar) {
    return new FullAdjustmentRecord(
        ar.getId(),
        SimplePact.fromPact(ar.getPact()),
        ar.getAdjustDate(),
        ar.getAdjustVersion());
  }

}
