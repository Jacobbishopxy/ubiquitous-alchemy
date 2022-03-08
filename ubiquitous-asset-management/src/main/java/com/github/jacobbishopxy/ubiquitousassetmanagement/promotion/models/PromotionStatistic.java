/**
 * Created by Jacob Xie on 2/22/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PromotionStatistic
 *
 * A promotion statistic is a summation of each promoter's performance during a
 * period of time.
 */
@Entity
@Table(name = "promotion_statistic")
public class PromotionStatistic {
  // =======================================================================
  // Fields
  // =======================================================================

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
  @Schema(description = "The total score of the promoter's performance during the period of time.", required = true)
  private Float totalScore;

  @Column(nullable = false)
  @Schema(description = "The total number of promotion count from all the previous promotion pacts.", required = true)
  private Integer previousPromotionCount;

  @Column(nullable = false)
  @Schema(description = "The total number of promotion count of the promoter's performance during the period of time.", required = true)
  private Integer promotionCount;

  @Column(nullable = false)
  @Schema(description = "The base score.", required = true)
  private Float baseScore;

  @Column(nullable = false)
  @Schema(description = "The performance score.", required = true)
  private Float performanceScore;

  @Column(nullable = false)
  @Schema(description = "The successful promotion.", required = true)
  private Integer promotionSuccessCount;

  @Column(nullable = false)
  @Schema(description = "The failed promotion count.", required = true)
  private Integer promotionFailureCount;

  @Column(nullable = false)
  @Schema(description = "The successful promotion rate.", required = true)
  private Float successRate;

  // =======================================================================
  // Constructors
  // =======================================================================

  public PromotionStatistic() {
  }

  public PromotionStatistic(
      Promoter promoter,
      Float totalScore,
      Integer previousPromotionCount,
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
    this.previousPromotionCount = previousPromotionCount;
    this.promotionCount = promotionCount;
    this.baseScore = baseScore;
    this.performanceScore = performanceScore;
    this.promotionSuccessCount = promotionSuccessCount;
    this.promotionFailureCount = promotionFailureCount;
    this.successRate = successRate;
    this.promotionPact = promotionPact;
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

  public Integer getPreviousPromotionCount() {
    return previousPromotionCount;
  }

  public void setPreviousPromotionCount(Integer previousPromotionCount) {
    this.previousPromotionCount = previousPromotionCount;
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
