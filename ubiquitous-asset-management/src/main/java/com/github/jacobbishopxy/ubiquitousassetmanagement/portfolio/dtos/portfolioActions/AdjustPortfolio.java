/**
 * Created by Jacob Xie on 3/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.portfolioActions;

import java.time.LocalDate;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Schema(name = "PortfolioActionAdjust", description = "Adjust portfolio")
public record AdjustPortfolio(
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate adjustDate,
		List<Constituent> constituents,
		List<Benchmark> benchmarks) {

}
