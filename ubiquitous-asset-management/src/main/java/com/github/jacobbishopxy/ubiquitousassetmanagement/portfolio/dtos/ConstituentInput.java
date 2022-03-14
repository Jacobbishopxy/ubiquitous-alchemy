/**
 * Created by Jacob Xie on 3/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

public record ConstituentInput(
    int adjustmentRecordId,
    LocalDate adjustDate,
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
