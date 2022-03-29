/**
 * Created by Jacob Xie on 3/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false, columnDefinition = "DATE")
  @NotEmpty
  @Schema(description = "The date of adjustment.", required = true)
  private LocalDate adjustDate;

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

  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private LocalDateTime createdAt;

  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private LocalDateTime updatedAt;

  // =======================================================================
  // Constructors
  // =======================================================================

  public Benchmark() {
  }

  public Benchmark(
      AdjustmentRecord adjustmentRecord,
      LocalDate adjustDate,
      String benchmarkName,
      String symbol,
      Float percentageChange,
      Float staticWeight,
      Float dynamicWeight) {
    super();
    this.adjustmentRecord = adjustmentRecord;
    this.adjustDate = adjustDate;
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
    this.adjustDate = source.adjustDate;
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

  public LocalDate getAdjustDate() {
    return adjustDate;
  }

  public void setAdjustDate(LocalDate adjustDate) {
    this.adjustDate = adjustDate;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

}
