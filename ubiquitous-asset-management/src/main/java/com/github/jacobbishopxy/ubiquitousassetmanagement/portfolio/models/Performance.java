/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper.PortfolioCalculationHelper;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_performance")
@Schema(name = "PortfolioPerformance", description = "Portfolio performance")
public class Performance {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "portfolio_adjustment_record_id")
  @NotEmpty
  @Schema(description = "This portfolio record belongs to a specific portfolio pact's adjustment record.", required = true)
  private AdjustmentRecord adjustmentRecord;

  @Column(nullable = true)
  @NotEmpty
  @Schema(description = "The earnings yield of custom portfolio.", required = true)
  private Float portfolioEarningsYield;

  @Column(nullable = true)
  @NotEmpty
  @Schema(description = "The earnings yield of benchmark.", required = true)
  private Float benchmarkEarningsYield;

  @Column(nullable = true)
  @NotEmpty
  @Schema(description = "The difference between custom portfolio and benchmark.", required = true)
  private Float alpha;

  // =======================================================================
  // Constructors
  // =======================================================================

  public Performance() {
  }

  public Performance(
      AdjustmentRecord adjustmentRecord,
      Float portfolioEarningsYield,
      Float benchmarkEarningsYield) {
    super();
    this.adjustmentRecord = adjustmentRecord;
    this.portfolioEarningsYield = portfolioEarningsYield;
    this.benchmarkEarningsYield = benchmarkEarningsYield;
    this.alpha = PortfolioCalculationHelper.calculateAlpha(portfolioEarningsYield, benchmarkEarningsYield);
  }

  // deep copy constructor
  public Performance(Performance source) {
    this.id = source.id;
    this.adjustmentRecord = source.adjustmentRecord;
    this.portfolioEarningsYield = source.portfolioEarningsYield;
    this.benchmarkEarningsYield = source.benchmarkEarningsYield;
    this.alpha = source.alpha;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AdjustmentRecord getAdjustmentRecord() {
    return adjustmentRecord;
  }

  public void setAdjustmentRecord(AdjustmentRecord adjustmentRecord) {
    this.adjustmentRecord = adjustmentRecord;
  }

  public Float getPortfolioEarningsYield() {
    return portfolioEarningsYield;
  }

  public void setPortfolioEarningsYield(Float portfolioEarningsYield) {
    this.portfolioEarningsYield = portfolioEarningsYield;
  }

  public Float getBenchmarkEarningsYield() {
    return benchmarkEarningsYield;
  }

  public void setBenchmarkEarningsYield(Float benchmarkEarningsYield) {
    this.benchmarkEarningsYield = benchmarkEarningsYield;
  }

  public Float getAlpha() {
    return alpha;
  }

  public void setAlpha(Float alpha) {
    this.alpha = alpha;
  }
}
