/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalTime;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AdjustmentInfoPatch(
    @JsonFormat(pattern = Constants.TIME_FORMAT) LocalTime adjustTime,
    String description) {

}
