/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_performance")
public class PortfolioPerformance {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "This portfolio record belongs to a specific portfolio pact")
  private PortfolioPact portfolioPact;

  @Column(nullable = false)
  @NotEmpty
  @JsonFormat(pattern = Constants.TIME_FORMAT)
  @Schema(description = "Current date.", required = true)
  private Date currentDate;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The earnings yield of custom portfolio. (static weight calculation)", required = true)
  private Float portfolioStaticEarningsYield;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The earnings yield of custom portfolio. (dynamic weight calculation)", required = true)
  private Float portfolioDynamicEarningsYield;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The earnings yield of benchmark. (static weight calculation)", required = true)
  private Float benchmarkStaticEarningsYield;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The earnings yield of benchmark. (static weight calculation)", required = true)
  private Float benchmarkDynamicEarningsYield;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The static difference between custom portfolio and benchmark.", required = true)
  private Float staticDifference;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The dynamic difference between custom portfolio and benchmark.", required = true)
  private Float dynamicDifference;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The version of this portfolio record.", required = true)
  private int version;

  public PortfolioPerformance() {
  }

  public PortfolioPerformance(
      PortfolioPact portfolioPact,
      Date currentDate,
      Float portfolioStaticEarningsYield,
      Float portfolioDynamicEarningsYield,
      Float benchmarkStaticEarningsYield,
      Float benchmarkDynamicEarningsYield,
      Float staticDifference,
      Float dynamicDifference,
      int version) {
    super();
    this.portfolioPact = portfolioPact;
    this.currentDate = currentDate;
    this.portfolioStaticEarningsYield = portfolioStaticEarningsYield;
    this.portfolioDynamicEarningsYield = portfolioDynamicEarningsYield;
    this.benchmarkStaticEarningsYield = benchmarkStaticEarningsYield;
    this.benchmarkDynamicEarningsYield = benchmarkDynamicEarningsYield;
    this.staticDifference = staticDifference;
    this.dynamicDifference = dynamicDifference;
    this.version = version;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PortfolioPact getPortfolioPact() {
    return portfolioPact;
  }

  public void setPortfolioPact(PortfolioPact portfolioPact) {
    this.portfolioPact = portfolioPact;
  }

  public Date getCurrentDate() {
    return currentDate;
  }

  public void setCurrentDate(Date currentDate) {
    this.currentDate = currentDate;
  }

  public Float getPortfolioStaticEarningsYield() {
    return portfolioStaticEarningsYield;
  }

  public void setPortfolioStaticEarningsYield(Float portfolioStaticEarningsYield) {
    this.portfolioStaticEarningsYield = portfolioStaticEarningsYield;
  }

  public Float getPortfolioDynamicEarningsYield() {
    return portfolioDynamicEarningsYield;
  }

  public void setPortfolioDynamicEarningsYield(Float portfolioDynamicEarningsYield) {
    this.portfolioDynamicEarningsYield = portfolioDynamicEarningsYield;
  }

  public Float getBenchmarkStaticEarningsYield() {
    return benchmarkStaticEarningsYield;
  }

  public void setBenchmarkStaticEarningsYield(Float benchmarkStaticEarningsYield) {
    this.benchmarkStaticEarningsYield = benchmarkStaticEarningsYield;
  }

  public Float getBenchmarkDynamicEarningsYield() {
    return benchmarkDynamicEarningsYield;
  }

  public void setBenchmarkDynamicEarningsYield(Float benchmarkDynamicEarningsYield) {
    this.benchmarkDynamicEarningsYield = benchmarkDynamicEarningsYield;
  }

  public Float getStaticDifference() {
    return staticDifference;
  }

  public void setStaticDifference(Float staticDifference) {
    this.staticDifference = staticDifference;
  }

  public Float getDynamicDifference() {
    return dynamicDifference;
  }

  public void setDynamicDifference(Float dynamicDifference) {
    this.dynamicDifference = dynamicDifference;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }
}
