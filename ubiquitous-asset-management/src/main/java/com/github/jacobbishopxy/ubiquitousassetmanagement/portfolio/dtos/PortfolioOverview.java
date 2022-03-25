/**
 * Created by Jacob Xie on 3/17/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Schema(name = "PortfolioOverview", description = "Portfolio overview")
public record PortfolioOverview(
		Long pactId,
		Long adjustmentRecordId,
		String alias,
		String promoterName,
		String industryName,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate startDate,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate endDate,
		String description,
		Float portfolioEarningsYield,
		Float benchmarkEarningsYield,
		Float alpha,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate adjustDate,
		Integer adjustVersion,
		Boolean isAdjusted) {

	public static PortfolioOverview fromPactAndPerformance(
			Pact pact,
			Performance performance) {

		AdjustmentRecord ar = performance.getAdjustmentRecord();

		return new PortfolioOverview(
				pact.getId(),
				ar.getId(),
				pact.getAlias(),
				pact.getPromoter().getNickname(),
				pact.getIndustryInfo().getName(),
				pact.getStartDate(),
				pact.getEndDate(),
				pact.getDescription(),
				performance.getPortfolioEarningsYield(),
				performance.getBenchmarkEarningsYield(),
				performance.getAlpha(),
				ar.getAdjustDate(),
				ar.getAdjustVersion(),
				ar.getIsAdjusted());
	}

}
