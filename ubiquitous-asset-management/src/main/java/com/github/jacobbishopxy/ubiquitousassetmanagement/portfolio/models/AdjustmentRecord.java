/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_adjustment_record", uniqueConstraints = {
    @UniqueConstraint(name = "unique_pact_id_adj_date_version", columnNames = { "portfolio_pact_id", "adjustDate",
        "adjustVersion" }),
    @UniqueConstraint(name = "unique_pact_id_is_unsettled", columnNames = { "portfolio_pact_id", "isUnsettled" })
})
@Schema(name = "PortfolioAdjustmentRecord", description = "Portfolio adjustment record. Used to record each settlement or current unsettled status (adjustVersion = 0) of a portfolio.")
public class AdjustmentRecord {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "portfolio_pact_id")
  @JsonIgnore
  private Pact pact;

  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = true, columnDefinition = "DATE")
  private LocalDate adjustDate;

  @Column(nullable = true)
  private Integer adjustVersion;

  @Column(nullable = true)
  private Boolean isUnsettled;

  @Column(nullable = true)
  private Boolean isAdjusted;

  // =======================================================================
  // Constructors
  // =======================================================================

  public AdjustmentRecord() {
  }

  public AdjustmentRecord(Long id) {
    this.id = id;
  }

  public AdjustmentRecord(
      Pact pact,
      LocalDate adjustDate,
      Integer adjustVersion,
      Boolean isUnsettled,
      Boolean isAdjusted) {
    super();
    this.pact = pact;
    this.adjustDate = adjustDate;
    this.adjustVersion = adjustVersion;
    this.isUnsettled = isUnsettled;
    this.isAdjusted = isAdjusted;
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

  public Pact getPact() {
    return pact;
  }

  public void setPact(Pact pact) {
    this.pact = pact;
  }

  public LocalDate getAdjustDate() {
    return adjustDate;
  }

  public void setAdjustDate(LocalDate adjustDate) {
    this.adjustDate = adjustDate;
  }

  public Integer getAdjustVersion() {
    return adjustVersion;
  }

  public void setAdjustVersion(Integer adjustVersion) {
    this.adjustVersion = adjustVersion;
  }

  public Boolean getIsUnsettled() {
    return isUnsettled;
  }

  public void setIsUnsettled(Boolean isUnsettled) {
    this.isUnsettled = isUnsettled;
  }

  public Boolean getIsAdjusted() {
    return isAdjusted;
  }

  public void setIsAdjusted(Boolean isAdjusted) {
    this.isAdjusted = isAdjusted;
  }

}
