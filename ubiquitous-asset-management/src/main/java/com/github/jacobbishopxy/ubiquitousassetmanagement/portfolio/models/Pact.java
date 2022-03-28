/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PactInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.IndustryInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "portfolio_pact", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "promoter_email", "startDate" }) })
@Schema(name = "PortfolioPact", description = "Portfolio meta information")
public class Pact {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(nullable = false)
  @Schema(description = "The name alias of the portfolio pact.", required = true)
  private String alias;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promoter_email")
  @Schema(description = "The promoter who is managing the portfolio. Nested object.", required = true)
  private Promoter promoter;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "industry_info_id")
  @Schema(description = "The industry of the portfolio.", required = true)
  private IndustryInfo industryInfo;

  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false, columnDefinition = "DATE")
  @NotEmpty
  @Schema(example = "2020-01-01", required = true)
  private LocalDate startDate;

  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(columnDefinition = "DATE")
  @Schema(example = "2020-12-31")
  private LocalDate endDate;

  @Schema(description = "The description of the portfolio pact.")
  private String description;

  @Schema(description = "Is the portfolio pact active?")
  private Boolean isActive;

  // =======================================================================
  // Constructors
  // =======================================================================

  public Pact() {
  }

  public Pact(Long id) {
    this.id = id;
  }

  public Pact(
      String alias,
      Promoter promoter,
      IndustryInfo industryInfo,
      LocalDate startDate,
      LocalDate endDate,
      String description,
      Boolean isActive) {
    super();
    this.alias = alias;
    this.promoter = promoter;
    this.industryInfo = industryInfo;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.isActive = isActive;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public static Pact fromPortfolioPactDto(
      PactInput portfolioPactInput,
      Promoter promoter,
      IndustryInfo industryInfo) {

    boolean isActive = portfolioPactInput.endDate() == null ? true : false;

    return new Pact(
        portfolioPactInput.alias(),
        promoter,
        industryInfo,
        portfolioPactInput.startDate(),
        portfolioPactInput.endDate(),
        portfolioPactInput.description(),
        isActive);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Promoter getPromoter() {
    return promoter;
  }

  public void setPromoter(Promoter promoter) {
    this.promoter = promoter;
  }

  public IndustryInfo getIndustryInfo() {
    return industryInfo;
  }

  public void setIndustryInfo(IndustryInfo industryInfo) {
    this.industryInfo = industryInfo;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

}
