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
        "adjustVersion" })
})
@Schema(name = "PortfolioAdjustmentRecord", description = "Portfolio adjustment record")
public class AdjustmentRecord {
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
  private Pact pact;

  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false, columnDefinition = "DATE")
  private LocalDate adjustDate;

  @Column(nullable = false)
  private Integer adjustVersion;

  // =======================================================================
  // Constructors
  // =======================================================================

  public AdjustmentRecord() {
  }

  public AdjustmentRecord(int id) {
    this.id = id;
  }

  public AdjustmentRecord(
      Pact pact,
      LocalDate adjustDate,
      Integer adjustVersion) {
    super();
    this.pact = pact;
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

}
