/**
 * Created by Jacob Xie on 3/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PortfolioDetail", description = "Portfolio detail")
public record PortfolioDetail(
		AdjustmentRecord adjustmentRecord,
		List<Constituent> constituents,
		List<Benchmark> benchmarks,
		Performance performance,
		List<AdjustmentInfo> adjustmentInfos) {

}
