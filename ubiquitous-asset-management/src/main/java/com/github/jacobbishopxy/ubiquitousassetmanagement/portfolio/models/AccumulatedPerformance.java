/**
 * Created by Jacob Xie on 3/26/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_accumulated_performance", uniqueConstraints = {
    @UniqueConstraint(name = "unique_pact_id", columnNames = { "portfolio_pact_id" })
})
public class AccumulatedPerformance {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "portfolio_pact_id")
  @NotEmpty
  @Schema(description = "This portfolio accumulated performance belongs to a specific portfolio pact.", required = true)
  @JsonIgnore
  private Pact pact;

  @Column(nullable = true)
  @Schema(description = "The earnings yield of custom portfolio.", required = true)
  private Float portfolioEarningsYield;

  @Column(nullable = true)
  @Schema(description = "The earnings yield of benchmark.", required = true)
  private Float benchmarkEarningsYield;

  @Column(nullable = true)
  @Schema(description = "The difference between custom portfolio and benchmark.", required = true)
  private Float alpha;

  @Column(nullable = false)
  @Schema(description = "The total number of the adjustments", required = true)
  private Integer adjustCount;

  // =======================================================================
  // Constructors
  // =======================================================================

  public AccumulatedPerformance() {
  }

  public AccumulatedPerformance(
      Pact pact,
      Float portfolioEarningsYield,
      Float benchmarkEarningsYield,
      Float alpha,
      Integer adjustCount) {
    super();
    this.pact = pact;
    this.portfolioEarningsYield = portfolioEarningsYield;
    this.benchmarkEarningsYield = benchmarkEarningsYield;
    this.alpha = alpha;
    this.adjustCount = adjustCount;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public Long getId() {
    return id;
  }

  public Pact getPact() {
    return pact;
  }

  public Long getThePactId() {
    return pact.getId();
  }

  public void setPact(Pact pact) {
    this.pact = pact;
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

  public Integer getAdjustCount() {
    return adjustCount;
  }

  public void setAdjustCount(Integer adjustCount) {
    this.adjustCount = adjustCount;
  }

}
