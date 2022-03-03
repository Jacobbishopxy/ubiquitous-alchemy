/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.util.Date;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.fields.PortfolioAdjustmentOperation;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.fields.PortfolioAdjustmentOperationPgEnum;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PortfolioAdjustment
 *
 * A portfolio adjustment is a portfolio adjusted record from a promoter's
 * perspective.
 */
@Entity
@Table(name = "portfolio_adjustment")
@TypeDef(name = "adjustment_operation_enum", typeClass = PortfolioAdjustmentOperationPgEnum.class)
public class PortfolioAdjustment {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @JsonFormat(pattern = Constants.TIME_FORMAT)
  @NotEmpty
  @Schema(description = "The date of the adjustment.", example = "2022-02-22 02:22:22", required = true)
  private Date adjustTime;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promoter_email")
  @NotEmpty
  @Schema(description = "The promoter who is adjusting the portfolio. Nested object.", required = true)
  private Promoter promoter;

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
  private PortfolioAdjustmentOperation operation;

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

  public PortfolioAdjustment() {
  }

  public PortfolioAdjustment(
      Date adjustTime,
      Promoter promoter,
      String symbol,
      String abbreviation,
      PortfolioAdjustmentOperation operation,
      Float weight,
      String description) {
    this.adjustTime = adjustTime;
    this.promoter = promoter;
    this.symbol = symbol;
    this.abbreviation = abbreviation;
    this.operation = operation;
    this.staticWeightChange = weight;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getAdjustTime() {
    return adjustTime;
  }

  public void setAdjustTime(Date adjustTime) {
    this.adjustTime = adjustTime;
  }

  public Promoter getPromoter() {
    return promoter;
  }

  public void setPromoter(Promoter promoter) {
    this.promoter = promoter;
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

  public PortfolioAdjustmentOperation getOperation() {
    return operation;
  }

  public void setOperation(PortfolioAdjustmentOperation operation) {
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
