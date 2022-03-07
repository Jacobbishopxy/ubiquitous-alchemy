/**
 * Created by Jacob Xie on 3/2/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.util.Date;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_benchmark")
public class PortfolioBenchmark {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "portfolio_pact_id")
  @NotEmpty
  @Schema(description = "This portfolio record belongs to a specific portfolio pact")
  private PortfolioPact portfolioPact;

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The date of adjustment.", required = true)
  private Date adjustDate;

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "Current date.", required = true)
  private Date presentDate;

  @Column(nullable = false)
  @NotEmpty
  private String benchmarkName;

  @Column(nullable = false)
  @NotEmpty
  private Float percentageChange;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The weight of this portfolio record.", required = true)
  private Float adjustDateWeight;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The weight of this portfolio record.", required = true)
  private Float currentWeight;

  public PortfolioBenchmark() {
  }

  public PortfolioBenchmark(
      PortfolioPact portfolioPact,
      Date adjustDate,
      Date presentDate,
      String benchmarkName,
      Float percentageChange,
      Float adjustDateWeight,
      Float currentWeight) {
    super();
    this.portfolioPact = portfolioPact;
    this.adjustDate = adjustDate;
    this.presentDate = presentDate;
    this.benchmarkName = benchmarkName;
    this.percentageChange = percentageChange;
    this.adjustDateWeight = adjustDateWeight;
    this.currentWeight = currentWeight;
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

  public Date getAdjustDate() {
    return adjustDate;
  }

  public void setAdjustDate(Date adjustDate) {
    this.adjustDate = adjustDate;
  }

  public Date getPresentDate() {
    return presentDate;
  }

  public void setPresentDate(Date presentDate) {
    this.presentDate = presentDate;
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

  public Float getAdjustDateWeight() {
    return adjustDateWeight;
  }

  public void setAdjustDateWeight(Float adjustDateWeight) {
    this.adjustDateWeight = adjustDateWeight;
  }

  public Float getCurrentWeight() {
    return currentWeight;
  }

  public void setCurrentWeight(Float currentWeight) {
    this.currentWeight = currentWeight;
  }

}
