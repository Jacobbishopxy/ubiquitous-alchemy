/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

@Entity
@Table(name = "portfolio_adjustment_record", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "portfolio_pact_id", "adjustDate", "adjustVersion" })
})
public class PortfolioAdjustmentRecord {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "portfolio_pact_id")
  @JsonIgnore
  private PortfolioPact portfolioPact;

  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false, columnDefinition = "DATE")
  private LocalDate adjustDate;

  @Column(nullable = false)
  private Integer adjustVersion;

  // =======================================================================
  // Constructors
  // =======================================================================

  public PortfolioAdjustmentRecord() {
  }

  public PortfolioAdjustmentRecord(
      PortfolioPact portfolioPact,
      LocalDate adjustDate,
      Integer adjustVersion) {
    super();
    this.portfolioPact = portfolioPact;
    this.adjustDate = adjustDate;
    this.adjustVersion = adjustVersion;
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

  public PortfolioPact getPortfolioPact() {
    return portfolioPact;
  }

  public void setPortfolioPact(PortfolioPact portfolioPact) {
    this.portfolioPact = portfolioPact;
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

}
