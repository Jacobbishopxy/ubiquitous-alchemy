/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.time.LocalTime;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.fields.AdjustmentOperation;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.fields.AdjustmentOperationPgEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AdjustmentInfo
 *
 * A portfolio adjustment is a portfolio adjusted record from a promoter's
 * perspective.
 */
@Entity
@Table(name = "portfolio_adjustment_info")
@TypeDef(name = "adjustment_operation_enum", typeClass = AdjustmentOperationPgEnum.class)
public class AdjustmentInfo {
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

  @JsonFormat(pattern = Constants.TIME_FORMAT)
  @Column(nullable = false, columnDefinition = "TIME")
  @NotEmpty
  @Schema(description = "The time of the adjustment.", example = "02:22:22", required = true)
  private LocalTime adjustTime;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The symbol of the asset.", required = true)
  private String symbol;

  @NotEmpty
  private String abbreviation;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Type(type = "adjustment_operation_enum")
  @NotEmpty
  @Schema(description = "The operation of the adjustment.", allowableValues = { "Setup", "Join", "Leave", "Increase",
      "Decrease" }, required = true)
  private AdjustmentOperation operation;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The static weight change of the adjustment.", example = "0.15", required = true)
  private Float staticWeightChange;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The dynamic weight change of the adjustment.", example = "0.15", required = true)
  private Float dynamicWeightChange;

  @Column(columnDefinition = "TEXT")
  private String description;

  // =======================================================================
  // Constructors
  // =======================================================================

  public AdjustmentInfo() {
  }

  public AdjustmentInfo(
      AdjustmentRecord adjustmentRecord,
      LocalTime adjustTime,
      String symbol,
      String abbreviation,
      AdjustmentOperation operation,
      Float weight,
      String description) {
    super();
    this.adjustmentRecord = adjustmentRecord;
    this.adjustTime = adjustTime;
    this.symbol = symbol;
    this.abbreviation = abbreviation;
    this.operation = operation;
    this.staticWeightChange = weight;
    this.description = description;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

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

  public LocalTime getAdjustTime() {
    return adjustTime;
  }

  public void setAdjustTime(LocalTime adjustTime) {
    this.adjustTime = adjustTime;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public AdjustmentOperation getOperation() {
    return operation;
  }

  public void setOperation(AdjustmentOperation operation) {
    this.operation = operation;
  }

  public Float getWeight() {
    return staticWeightChange;
  }

  public void setWeight(Float weight) {
    this.staticWeightChange = weight;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
