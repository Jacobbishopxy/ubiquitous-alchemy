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

public record PortfolioDto(
        AdjustmentRecord adjustmentRecord,
        List<Benchmark> benchmarks,
        List<Constituent> constituents,
        Performance performance,
        List<AdjustmentInfo> adjustmentInfos) {

}
