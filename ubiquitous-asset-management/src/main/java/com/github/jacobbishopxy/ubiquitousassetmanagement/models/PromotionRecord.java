/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordDto;

import jakarta.persistence.*;

@Entity
@Table(name = "promotion_record")
public class PromotionRecord {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promoter_email")
  private Promoter promoter;

  @Column(nullable = false)
  private String symbol;

  private String abbreviation;

  private String industry;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Direction direction;

  @Column(nullable = false)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  private Date openTime;

  @Column(nullable = false)
  private Float openPrice;

  @JsonFormat(pattern = Constants.DATE_FORMAT)
  private Date closeTime;

  private Float closePrice;

  private Float earningsYield;

  private Float score;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promotion_pact_id")
  private PromotionPact promotionPact;

  private Boolean isArchived;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  private Date updatedAt;

  public PromotionRecord() {
  }

  public PromotionRecord(
      Promoter promoter,
      String symbol,
      String abbreviation,
      String industry,
      Direction direction,
      Date openTime,
      Float openPrice,
      Date closeTime,
      Float closePrice,
      Float earningsYield,
      Float score,
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
    this.earningsYield = earningsYield;
    this.score = score;
    this.promotionPact = promotionPact;
    this.isArchived = isArchived;
  }

  public static PromotionRecord fromPromotionRecordDtoAndEmail(
      PromotionRecordDto dto,
      String email,
      Integer promotionPactId) {
    return new PromotionRecord(
        new Promoter(email),
        dto.symbol(),
        dto.abbreviation(),
        dto.industry(),
        dto.direction(),
        dto.openTime(),
        dto.openPrice(),
        dto.closeTime(),
        dto.closePrice(),
        dto.earningsYield(),
        dto.score(),
        new PromotionPact(promotionPactId),
        dto.isArchived());
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

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
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

  public Float getEarningsYield() {
    return earningsYield;
  }

  public void setEarningsYield(Float earningsYield) {
    this.earningsYield = earningsYield;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
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
