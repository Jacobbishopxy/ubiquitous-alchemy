/**
 * Created by Jacob Xie on 3/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AccumulatedPerformance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Performance;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PortfolioDetail", description = "Portfolio detail")
public record PortfolioDetail(
		AdjustmentRecord adjustmentRecord,
		List<Constituent> constituents,
		List<Benchmark> benchmarks,
		Performance performance,
		List<AdjustmentInfo> adjustmentInfos,
		AccumulatedPerformance accumulatedPerformance) {

}
