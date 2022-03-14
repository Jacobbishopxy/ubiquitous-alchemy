/**
 * Created by Jacob Xie on 3/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.BenchmarkInput;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_benchmark")
public class Benchmark {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

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
      LocalDate adjustDate,
      String benchmarkName,
      Float percentageChange,
      Float staticWeight,
      Float dynamicWeight) {
    super();
    this.adjustmentRecord = adjustmentRecord;
    this.adjustDate = adjustDate;
    this.benchmarkName = benchmarkName;
    this.percentageChange = percentageChange;
    this.staticWeight = staticWeight;
    this.dynamicWeight = dynamicWeight;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public int get_adjustment_record_id() {
    Integer id = adjustmentRecord.getId();
    if (id == null) {
      throw new IllegalStateException("The adjustment record id cannot be null.");
    }
    return id;
  }

  public Benchmark fromBenchmarkDto(BenchmarkInput dto) {

    AdjustmentRecord ar = new AdjustmentRecord();
    ar.setId(dto.adjustmentRecordId());

    Float expansionRate = dto.weight() * dto.percentageChange();

    return new Benchmark(
        ar,
        dto.adjustDate(),
        dto.benchmarkName(),
        dto.percentageChange(),
        dto.weight(),
        expansionRate);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
