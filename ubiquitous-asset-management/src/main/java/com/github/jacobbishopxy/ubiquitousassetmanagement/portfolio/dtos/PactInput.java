/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;

public record PactInput(
    String alias,
    String promoter,
    String industry,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate startDate,
    @JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate endDate,
    String description) {

}
