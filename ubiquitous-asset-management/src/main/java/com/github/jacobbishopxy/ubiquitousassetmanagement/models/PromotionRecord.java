/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "promotion_record")
public class PromotionRecord {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private String promoter;

  @Column(nullable = false)
  private String symbol;

  private String abbreviation;

  private String industry;

  @Column(nullable = false)
  private String direction;

  @Column(nullable = false)
  private Date openTime;

  @Column(nullable = false)
  private Float openPrice;

  private Date closeTime;

  private Float closePrice;

  private String earningsYield;

  private Float score;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updatedAt;

  public PromotionRecord() {
  }

  public PromotionRecord(
      String promoter,
      String symbol,
      String direction,
      Date openTime,
      Float openPrice) {
    super();
    this.promoter = promoter;
    this.symbol = symbol;
    this.direction = direction;
    this.openTime = openTime;
    this.openPrice = openPrice;
  }

  public Integer getId() {
    return id;
  }

  public String getPromoter() {
    return promoter;
  }

  public void setPromoter(String promoter) {
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

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
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

  public String getEarningsYield() {
    return earningsYield;
  }

  public void setEarningsYield(String earningsYield) {
    this.earningsYield = earningsYield;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
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
