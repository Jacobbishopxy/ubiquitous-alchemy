/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalTime;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Schema(name = "PortfolioAdjustmentInfoUpdate", description = "Adjustment info update")
public record AdjustmentInfoUpdate(
		@JsonFormat(pattern = Constants.TIME_FORMAT) LocalTime adjustTime,
		String description) {

}
