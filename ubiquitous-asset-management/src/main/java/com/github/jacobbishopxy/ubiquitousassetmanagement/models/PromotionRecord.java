/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.fields.PerformanceScore;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.fields.TradeDirection;
import com.github.jacobbishopxy.ubiquitousassetmanagement.services.helper.PromotionCalculationHelper;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * PromotionRecord
 * 
 * A promotion record is a record from a promoter's perspective.
 */
@Entity
@Table(name = "promotion_record")
public class PromotionRecord {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promoter_email")
  @NotEmpty
  @Schema(description = "The promoter who is promoting the asset. Nested object.")
  private Promoter promoter;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promotion_pact_name")
  @NotEmpty
  @Schema(description = "The promotion pact which the promoter is promoting. Nested object.")
  private PromotionPact promotionPact;

  @Column(nullable = false)
  private String symbol;

  private String abbreviation;

  private String industry;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Schema(description = "The direction of the trade.", allowableValues = { "BUY", "SELL" }, required = true)
  private TradeDirection direction;

  @Column(nullable = false)
  @JsonFormat(pattern = Constants.TIME_FORMAT)
  @NotEmpty
  @Schema(description = "The time when the promotion record is created.", example = "2020-02-22 09:45:00", required = true)
  private Date openTime;

  @Column(nullable = false)
  @NotEmpty
  private Float openPrice;

  @JsonFormat(pattern = Constants.TIME_FORMAT)
  @Schema(description = "The time when the promotion record is closed.", example = "2020-02-23 10:05:00")
  private Date closeTime;

  private Float closePrice;

  private Float adjustFactor;

  private Float earningsYield;

  private Integer performanceScore;

  private Boolean isArchived;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.TIME_FORMAT)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.TIME_FORMAT)
  private Date updatedAt;

  public PromotionRecord() {
  }

  public PromotionRecord(
      Promoter promoter,
      String symbol,
      String abbreviation,
      String industry,
      TradeDirection direction,
      Date openTime,
      Float openPrice,
      Date closeTime,
      Float closePrice,
      Float adjustFactor,
      Float earningsYield,
      Integer performanceScore,
      PromotionPact promotionPact,
      Boolean isArchived) {
    super();
    this.promoter = promoter;
    this.symbol = symbol;
    this.abbreviation = abbreviation;
    this.industry = industry;
    this.direction = direction;
    this.openTime = openTime;
    this.openPrice = openPrice;
    this.closeTime = closeTime;
    this.closePrice = closePrice;
    this.adjustFactor = adjustFactor;
    this.earningsYield = earningsYield;
    this.performanceScore = performanceScore;
    this.promotionPact = promotionPact;
    this.isArchived = isArchived;
  }

  public static PromotionRecord fromPromotionRecordDto(
      PromotionRecordInput dto,
      String promoterEmail,
      String promotionPactName) {

    Float earningsYield = PromotionCalculationHelper.calculateEarningsYield(
        dto.direction(),
        dto.openPrice(),
        dto.closePrice(),
        dto.adjustFactor());

    boolean isArchived = PromotionCalculationHelper.calculateIsArchived(
        dto.closeTime());

    PerformanceScore performanceScore = PromotionCalculationHelper.calculatePerformanceScore(
        isArchived,
        earningsYield);
    Integer pScore = performanceScore == null ? null : performanceScore.score();

    return new PromotionRecord(
        new Promoter(promoterEmail),
        dto.symbol(),
        dto.abbreviation(),
        dto.industry(),
        dto.direction(),
        dto.openTime(),
        dto.openPrice(),
        dto.closeTime(),
        dto.closePrice(),
        dto.adjustFactor(),
        earningsYield,
        pScore,
        new PromotionPact(promotionPactName),
        isArchived);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public TradeDirection getDirection() {
    return direction;
  }

  public void setDirection(TradeDirection direction) {
    this.direction = direction;
  }

  public Date getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Date openTime) {
    this.openTime = openTime;
  }

  public Float getOpenPrice() {
    return openPrice;
  }

  public void setOpenPrice(Float openPrice) {
    this.openPrice = openPrice;
  }

  public Date getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(Date closeTime) {
    this.closeTime = closeTime;
  }

  public Float getClosePrice() {
    return closePrice;
  }

  public void setClosePrice(Float closePrice) {
    this.closePrice = closePrice;
  }

  public Float getAdjustFactor() {
    return adjustFactor;
  }

  public void setAdjustFactor(Float adjustFactor) {
    this.adjustFactor = adjustFactor;
  }

  public Float getEarningsYield() {
    return earningsYield;
  }

  public void setEarningsYield() {
    this.earningsYield = PromotionCalculationHelper.calculateEarningsYield(
        direction,
        openPrice,
        closePrice,
        adjustFactor);
  }

  public Integer getPerformanceScore() {
    return performanceScore;
  }

  public void setPerformanceScore(Integer performanceScore) {
    this.performanceScore = performanceScore;
  }

  public PromotionPact getPromotionPact() {
    return promotionPact;
  }

  public void setPromotionPact(PromotionPact promotionPact) {
    this.promotionPact = promotionPact;
  }

  public boolean getIsArchived() {
    return isArchived;
  }

  public void setIsArchived(boolean archived) {
    this.isArchived = archived;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Date();
  }

}
