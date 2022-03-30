/**
 * Created by Jacob Xie on 3/25/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PortfolioBenchmarkUpdate", description = "Benchmark update")
public record BenchmarkUpdate(Float percentageChange) {

}
