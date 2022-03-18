/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.ConstituentInput;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "portfolio_constituent")
public class Constituent {
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
  @Schema(description = "The symbol code.", required = true)
  private String symbol;

  @Schema(description = "The symbol abbreviation.", required = true)
  private String abbreviation;

  @Column(nullable = false)
  @NotEmpty
  private Float adjustDatePrice;

  @Column(nullable = false)
  @NotEmpty
  private Float currentPrice;

  @Column(nullable = false)
  @NotEmpty
  private Float adjustDateFactor;

  @Column(nullable = false)
  @NotEmpty
  private Float currentFactor;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The static weight of this portfolio record.", required = true)
  private Float staticWeight;

  @Column(nullable = false)
  @NotEmpty
  @Schema(description = "The dynamic weight of this portfolio record.", required = true)
  private Float dynamicWeight;

  @Column(nullable = false)
  @NotEmpty
  private Float pbpe;

  @Column(nullable = false)
  @NotEmpty
  private Float marketValue;

  @Column(nullable = false)
  @NotEmpty
  private Float earningsYield;

  // =======================================================================
  // Constructors
  // =======================================================================

  public Constituent() {
  }

  public Constituent(
      AdjustmentRecord adjustmentRecord,
      LocalDate adjustDate,
      String symbol,
      String abbreviation,
      Float adjustDatePrice,
      Float currentPrice,
      Float adjustDateFactor,
      Float currentFactor,
      Float adjustDateWeight,
      Float currentWeight,
      Float pbpe,
      Float marketValue,
      Float earningsYield) {
    super();
    this.adjustmentRecord = adjustmentRecord;
    this.adjustDate = adjustDate;
    this.symbol = symbol;
    this.abbreviation = abbreviation;
    this.adjustDatePrice = adjustDatePrice;
    this.currentPrice = currentPrice;
    this.adjustDateFactor = adjustDateFactor;
    this.currentFactor = currentFactor;
    this.staticWeight = adjustDateWeight;
    this.dynamicWeight = currentWeight;
    this.pbpe = pbpe;
    this.marketValue = marketValue;
    this.earningsYield = earningsYield;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public int getAdjustmentRecordId() {
    Integer id = adjustmentRecord.getId();
    if (id == null) {
      throw new IllegalArgumentException("The adjustment record id cannot be null.");
    }
    return id;
  }

  public Constituent fromConstituentDto(ConstituentInput dto) {

    AdjustmentRecord ar = new AdjustmentRecord();
    ar.setId(dto.adjustmentRecordId());

    Float earningsYield = 0f;
    Float pctChg = 0f;
    if (dto.adjustDatePrice() != null &&
        dto.currentPrice() != null &&
        dto.adjustDateFactor() != null &&
        dto.currentFactor() != null) {
      Float adjChg = dto.currentFactor() / dto.adjustDateFactor();
      pctChg = adjChg * dto.currentPrice() / dto.adjustDatePrice();
      earningsYield = pctChg - 1;
    }
    // since we not yet have the totalPctChg, saving expansionRate in order to
    // calculate the currentWeight afterwards
    Float expansionRate = dto.weight() * pctChg;

    return new Constituent(
        ar,
        dto.adjustDate(),
        dto.symbol(),
        dto.abbreviation(),
        dto.adjustDatePrice(),
        dto.currentPrice(),
        dto.adjustDateFactor(),
        dto.currentFactor(),
        dto.weight(),
        expansionRate, // currentWeight = expansionRate / sum of all expansionRates
        dto.pbpe(),
        dto.marketValue(),
        earningsYield);
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

  public Float getAdjustDatePrice() {
    return adjustDatePrice;
  }

  public void setAdjustDatePrice(Float adjustDatePrice) {
    this.adjustDatePrice = adjustDatePrice;
  }

  public Float getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(Float currentPrice) {
    this.currentPrice = currentPrice;
  }

  public Float getAdjustDateFactor() {
    return adjustDateFactor;
  }

  public void setAdjustDateFactor(Float adjustDateFactor) {
    this.adjustDateFactor = adjustDateFactor;
  }

  public Float getCurrentFactor() {
    return currentFactor;
  }

  public void setCurrentFactor(Float currentFactor) {
    this.currentFactor = currentFactor;
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

  public Float getPbpe() {
    return pbpe;
  }

  public void setPbpe(Float pbpe) {
    this.pbpe = pbpe;
  }

  public Float getMarketValue() {
    return marketValue;
  }

  public void setMarketValue(Float marketValue) {
    this.marketValue = marketValue;
  }

  public Float getEarningsYield() {
    return earningsYield;
  }

  public void setEarningsYield(Float earningsYield) {
    this.earningsYield = earningsYield;
  }

}
