/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.IndustryInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "portfolio_pact", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "promoter_email", "startDate" }) })
public class PortfolioPact {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

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

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  @NotEmpty
  @Schema(example = "2020-01-01", required = true)
  private Date startDate;

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Schema(example = "2020-12-31")
  private Date endDate;

  @Schema(description = "The description of the portfolio pact.")
  private String description;

  @Schema(description = "Is the portfolio pact active?")
  private Boolean isActive;

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The last updated date of the portfolio.", required = true)
  private Date lastUpdatedDate;

  // =======================================================================
  // Constructors
  // =======================================================================

  public PortfolioPact() {
  }

  public PortfolioPact(
      String alias,
      Promoter promoter,
      IndustryInfo industryInfo,
      Date startDate,
      Date endDate,
      String description,
      Boolean isActive,
      Date lastUpdatedDate) {
    super();
    this.alias = alias;
    this.promoter = promoter;
    this.industryInfo = industryInfo;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.isActive = isActive;
    this.lastUpdatedDate = lastUpdatedDate;
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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
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

  public Date getLastUpdatedDate() {
    return lastUpdatedDate;
  }

  public void setLastUpdatedDate(Date lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
  }

}
