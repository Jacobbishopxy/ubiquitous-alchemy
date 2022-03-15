/**
 * Created by Jacob Xie on 3/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BenchmarkInput(
    int adjustmentRecordId,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate adjustDate,
    String benchmarkName,
    Float percentageChange,
    Float weight) {

}
