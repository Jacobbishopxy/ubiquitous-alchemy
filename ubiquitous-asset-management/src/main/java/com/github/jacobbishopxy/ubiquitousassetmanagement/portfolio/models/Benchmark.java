/**
 * Created by Jacob Xie on 3/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_benchmark")
@Schema(name = "PortfolioBenchmark", description = "Portfolio benchmark")
public class Benchmark {
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

  @Column(nullable = false)
  @NotEmpty
  private String benchmarkName;

  @Column(nullable = false)
  @NotEmpty
  private String symbol;

  @Column(nullable = false)
  @NotEmpty
  private Float percentageChange;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The static weight of this portfolio record.", required = true)
  private Float staticWeight;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The dynamic weight of this portfolio record.", required = true)
  private Float dynamicWeight;

  // =======================================================================
  // Constructors
  // =======================================================================

  public Benchmark() {
  }

  public Benchmark(
      AdjustmentRecord adjustmentRecord,
      String benchmarkName,
      String symbol,
      Float percentageChange,
      Float staticWeight,
      Float dynamicWeight) {
    super();
    this.adjustmentRecord = adjustmentRecord;
    this.benchmarkName = benchmarkName;
    this.symbol = symbol;
    this.percentageChange = percentageChange;
    this.staticWeight = staticWeight;
    this.dynamicWeight = dynamicWeight;
  }

  // deep copy constructor
  public Benchmark(Benchmark source) {
    this.id = source.id;
    this.adjustmentRecord = source.adjustmentRecord;
    this.benchmarkName = source.benchmarkName;
    this.symbol = source.symbol;
    this.percentageChange = source.percentageChange;
    this.staticWeight = source.staticWeight;
    this.dynamicWeight = source.dynamicWeight;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public Long getAdjRecordId() {
    Long id = adjustmentRecord.getId();
    if (id == null) {
      throw new IllegalStateException("The adjustment record id cannot be null.");
    }
    return id;
  }

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

  public String getBenchmarkName() {
    return benchmarkName;
  }

  public void setBenchmarkName(String benchmarkName) {
    this.benchmarkName = benchmarkName;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Float getPercentageChange() {
    return percentageChange;
  }

  public void setPercentageChange(Float percentageChange) {
    this.percentageChange = percentageChange;
  }

  public Float getStaticWeight() {
    return staticWeight;
  }

  public void setStaticWeight(Float staticWeight) {
    this.staticWeight = staticWeight;
  }

  public Float getDynamicWeight() {
    return dynamicWeight;
  }

  public void setDynamicWeight(Float dynamicWeight) {
    this.dynamicWeight = dynamicWeight;
  }

}
