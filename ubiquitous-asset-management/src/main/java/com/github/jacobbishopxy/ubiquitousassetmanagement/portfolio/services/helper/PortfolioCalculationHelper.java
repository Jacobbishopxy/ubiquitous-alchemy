/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;

public class PortfolioCalculationHelper {

  public record ConstituentsResult(
      List<Constituent> constituents,
      Float earningsYield) {
  }

  public static ConstituentsResult calculatePortfolioEarningsYield(
      List<Constituent> constituents) {

    Float totalExpansion = constituents
        .stream()
        .map(Constituent::getDynamicWeight)
        .reduce(0f, Float::sum);

    Float earningsYield = constituents
        .stream()
        .map(c -> {
          Float ey = c.getEarningsYield();
          if (ey == null) {
            ey = 0f;
          }
          return c.getStaticWeight() * ey;
        }).reduce(0f, Float::sum);

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

  public static BenchmarksResult calculateBenchmarkEarningsYield(
      List<Benchmark> benchmarks) {

    Float totalExpansion = benchmarks
        .stream()
        .map(Benchmark::getDynamicWeight)
        .reduce(0f, Float::sum);

    Float earningsYield = benchmarks
        .stream()
        .map(b -> {
          Float ey = b.getPercentageChange();
          if (ey == null) {
            ey = 0f;
          }
          return b.getStaticWeight() * ey;
        }).reduce(0f, Float::sum);

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
}
