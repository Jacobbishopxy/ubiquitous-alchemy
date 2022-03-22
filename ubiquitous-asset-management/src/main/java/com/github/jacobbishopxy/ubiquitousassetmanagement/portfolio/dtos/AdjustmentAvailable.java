/**
 * Created by Jacob Xie on 3/22/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Schema(name = "PortfolioAdjustmentAvailable", description = "Wether the adjustment is available")
public record AdjustmentAvailable(
		boolean available,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate adjustDate,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate latestAdjustDate,
		Integer latestAdjustVersion) {

}
