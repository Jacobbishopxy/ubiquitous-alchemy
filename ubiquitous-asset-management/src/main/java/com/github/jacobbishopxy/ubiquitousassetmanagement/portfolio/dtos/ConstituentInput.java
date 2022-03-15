/**
 * Created by Jacob Xie on 3/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ConstituentInput(
    int adjustmentRecordId,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate adjustDate,
    String symbol,
    String abbreviation,
    Float adjustDatePrice,
    Float currentPrice,
    Float adjustDateFactor,
    Float currentFactor,
    Float weight,
    Float pbpe,
    Float marketValue) {

}
