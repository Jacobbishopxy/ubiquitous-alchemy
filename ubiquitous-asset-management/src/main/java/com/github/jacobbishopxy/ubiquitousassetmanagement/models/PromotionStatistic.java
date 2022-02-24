/**
 * Created by Jacob Xie on 2/22/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import jakarta.persistence.*;

@Entity
@Table(name = "promotion_statistic")
public class PromotionStatistic {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promoter_email")
  private Promoter promoter;

  @Column(nullable = false)
  private Float totalScore;

  @Column(nullable = false)
  private Integer promotionCount;

  @Column(nullable = false)
  private Float baseScore;

  @Column(nullable = false)
  private Float performanceScore;

  @Column(nullable = false)
  private Integer promotionSuccessCount;

  @Column(nullable = false)
  private Integer promotionFailureCount;

  @Column(nullable = false)
  private Float successRate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promotion_pact_name")
  private PromotionPact promotionPact;

  public PromotionStatistic() {
  }

  public PromotionStatistic(
      Promoter promoter,
      Float totalScore,
      Integer promotionCount,
      Float baseScore,
      Float performanceScore,
      Integer promotionSuccessCount,
      Integer promotionFailureCount,
      Float successRate,
      PromotionPact promotionPact) {
    super();
    this.promoter = promoter;
    this.totalScore = totalScore;
    this.promotionCount = promotionCount;
    this.baseScore = baseScore;
    this.performanceScore = performanceScore;
    this.promotionSuccessCount = promotionSuccessCount;
    this.promotionFailureCount = promotionFailureCount;
    this.successRate = successRate;
    this.promotionPact = promotionPact;
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

  public Float getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(Float totalScore) {
    this.totalScore = totalScore;
  }

  public Integer getPromotionCount() {
    return promotionCount;
  }

  public void setPromotionCount(Integer promotionCount) {
    this.promotionCount = promotionCount;
  }

  public Float getBaseScore() {
    return baseScore;
  }

  public void setBaseScore(Float baseScore) {
    this.baseScore = baseScore;
  }

  public Float getPerformanceScore() {
    return performanceScore;
  }

  public void setPerformanceScore(Float performanceScore) {
    this.performanceScore = performanceScore;
  }

  public Integer getPromotionSuccessCount() {
    return promotionSuccessCount;
  }

  public void setPromotionSuccessCount(Integer promotionSuccessCount) {
    this.promotionSuccessCount = promotionSuccessCount;
  }

  public Integer getPromotionFailureCount() {
    return promotionFailureCount;
  }

  public void setPromotionFailureCount(Integer promotionFailureCount) {
    this.promotionFailureCount = promotionFailureCount;
  }

  public Float getSuccessRate() {
    return successRate;
  }

  public void setSuccessRate(Float successRate) {
    this.successRate = successRate;
  }

  public PromotionPact getPromotionPact() {
    return promotionPact;
  }

  public void setPromotionPact(PromotionPact promotionPact) {
    this.promotionPact = promotionPact;
  }

}
