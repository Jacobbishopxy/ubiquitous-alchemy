/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.helper;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Performance;

public class PortfolioCalculationHelper {

  public record ConstituentsResult(
      List<Constituent> constituents,
      Float earningsYield) {
  }

  public static ConstituentsResult modifyConstituentsAndCalculatePortfolioEarningsYield(
      List<Constituent> constituents) {

    Float totalExpansion = constituents
        .stream()
        .map(Constituent::getDynamicWeight)
        .reduce(0f, (t, u) -> Float.sum(t, u));

    Float earningsYield = constituents
        .stream()
        .map(c -> {
          Float ey = c.getEarningsYield();
          if (ey == null) {
            ey = 0f;
          }
          return c.getStaticWeight() * ey;
        })
        .reduce(0f, (t, u) -> Float.sum(t, u));

    constituents.stream().map(c -> {
      c.setDynamicWeight(c.getDynamicWeight() / totalExpansion);
      return c;
    });

    return new ConstituentsResult(constituents, earningsYield);
  }

  public record BenchmarksResult(
      List<Benchmark> benchmarks,
      Float earningsYield) {
  }

  public static BenchmarksResult modifyBenchmarksAndCalculateBenchmarkEarningsYield(
      List<Benchmark> benchmarks) {

    Float totalExpansion = benchmarks
        .stream()
        .map(Benchmark::getDynamicWeight)
        .reduce(0f, (t, u) -> Float.sum(t, u));

    Float earningsYield = benchmarks
        .stream()
        .map(b -> {
          Float ey = b.getPercentageChange();
          if (ey == null) {
            ey = 0f;
          }
          return b.getStaticWeight() * ey;
        })
        .reduce(0f, (t, u) -> Float.sum(t, u));

    benchmarks.stream().map(b -> {
      b.setDynamicWeight(b.getDynamicWeight() / totalExpansion);
      return b;
    });

    return new BenchmarksResult(benchmarks, earningsYield);
  }

  public static Float calculateAlpha(
      Float portfolioEarningsYield,
      Float benchmarkEarningsYield) {
    return portfolioEarningsYield - benchmarkEarningsYield;
  }

  public record AccumulatedPerformanceResult(
      Float portfolioEarningsYield,
      Float benchmarkEarningsYield,
      Float alpha) {
  }

  public static AccumulatedPerformanceResult calculateAccumulatedPerformance(List<Performance> performances) {

    Float accumulatedPortfolioEarningsYield = performances
        .stream()
        .map(Performance::getPortfolioEarningsYield)
        .reduce(1f, (subtotal, ele) -> {
          if (ele == null) {
            ele = 0f;
          }
          return subtotal * (1 + ele);
        });
    accumulatedPortfolioEarningsYield -= 1;

    Float accumulatedBenchmarkEarningsYield = performances
        .stream()
        .map(Performance::getBenchmarkEarningsYield)
        .reduce(1f, (subtotal, ele) -> {
          if (ele == null) {
            ele = 0f;
          }
          return subtotal * (1 + ele);
        });
    accumulatedBenchmarkEarningsYield -= 1;

    Float accumulatedAlpha = calculateAlpha(
        accumulatedPortfolioEarningsYield,
        accumulatedBenchmarkEarningsYield);

    return new AccumulatedPerformanceResult(
        accumulatedPortfolioEarningsYield,
        accumulatedBenchmarkEarningsYield,
        accumulatedAlpha);
  }
}
