/**
 * Created by Jacob Xie on 3/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;

public record PortfolioAdjust(
    LocalDate adjustDate,
    List<Constituent> constituents,
    List<Benchmark> benchmarks) {

}
