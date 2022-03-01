/**
 * Created by Jacob Xie on 3/1/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionStatistic;

public record PromotionStatisticOutput(
    int id,
    String promotionPactName,
    String promoter,
    Float totalScore,
    Integer previousPromotionCount,
    Integer promotionCount,
    Float baseScore,
    Float performanceScore,
    Integer promotionSuccessCount,
    Integer promotionFailureCount,
    Float successRate) {

  public static PromotionStatisticOutput fromPromotionStatistic(PromotionStatistic promotionStatistic) {
    return new PromotionStatisticOutput(
        promotionStatistic.getId(),
        promotionStatistic.getPromotionPact().getName(),
        promotionStatistic.getPromoter().getNickname(),
        promotionStatistic.getTotalScore(),
        promotionStatistic.getPreviousPromotionCount(),
        promotionStatistic.getPromotionCount(),
        promotionStatistic.getBaseScore(),
        promotionStatistic.getPerformanceScore(),
        promotionStatistic.getPromotionSuccessCount(),
        promotionStatistic.getPromotionFailureCount(),
        promotionStatistic.getSuccessRate());
  }

}
