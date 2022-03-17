/**
 * Created by Jacob Xie on 3/17/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;

public record Overview(
		int pactId,
		int adjustmentRecordId,
		String promoterName,
		Float portfolioEarningsYield,
		Float benchmarkEarningsYield,
		Float alpha,
		LocalDate adjustDate,
		int adjustVersion) {

	public static Overview fromPerformance(Performance performance) {
		return null;
	}

}
