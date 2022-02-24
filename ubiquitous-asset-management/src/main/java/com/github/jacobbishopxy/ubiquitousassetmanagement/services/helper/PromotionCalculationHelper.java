/**
 * Created by Jacob Xie on 2/24/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services.helper;

import java.util.Date;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.fields.PerformanceScore;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.fields.TradeDirection;

/**
 * PromotionCalculationHelper
 *
 * - calculateEarningsYield
 * - calculateIsArchived
 * - calculatePerformanceScore
 * - affectPromotionStatistic
 */
public class PromotionCalculationHelper {

  static Float baseScoreFactor = 2.5f;

  public enum AffectPromotionStatisticType {
    CREATE, UPDATE, DELETE
  }

  public static Float calculateEarningsYield(
      TradeDirection direction,
      Float openPrice,
      Float closePrice,
      Float adjustFactor) {
    if (direction == TradeDirection.BUY) {
      return (closePrice - openPrice) / openPrice * adjustFactor;
    } else {
      return (openPrice - closePrice) / openPrice * adjustFactor;
    }
  }

  public static boolean calculateIsArchived(Date closeTime) {
    return closeTime == null;
  }

  public static PerformanceScore calculatePerformanceScore(
      Boolean isArchived,
      Float earningsYield) {
    if (isArchived) {
      return PerformanceScore.fromEarningsYield(earningsYield);
    } else {
      return null;
    }
  }

  private static PromotionStatistic affectPromotionStatistic(
      PromotionRecord promotionRecord,
      PromotionStatistic promotionStatistic,
      List<PromotionRecord> relativePromotionRecord) {

    // length of relativePromotionRecord
    int relativePromotionRecordSize = relativePromotionRecord.size();
    // set promoter
    promotionStatistic.setPromoter(promotionRecord.getPromoter());
    // set promotionCount
    promotionStatistic.setPromotionCount(relativePromotionRecordSize);
    // set baseScore
    Float baseScore = relativePromotionRecordSize * PromotionCalculationHelper.baseScoreFactor;
    promotionStatistic.setBaseScore(baseScore);
    // set performanceScore
    Integer performanceScore = 0;
    if (relativePromotionRecordSize != 0) {
      performanceScore = relativePromotionRecord
          .stream()
          .filter(item -> item.getPerformanceScore() != null)
          .mapToInt(item -> item.getPerformanceScore())
          .sum();
    }
    promotionStatistic.setPerformanceScore(performanceScore.floatValue());
    // set totalScore
    promotionStatistic.setTotalScore(baseScore + performanceScore);
    // set promotionSuccessCount & promotionFailureCount
    Integer promotionSuccessCount = 0;
    Integer promotionFailureCount = 0;
    for (PromotionRecord pr : relativePromotionRecord) {
      if (pr.getPerformanceScore() > 0) {
        promotionSuccessCount++;
      } else if (pr.getPerformanceScore() < 0) {
        promotionFailureCount++;
      }
    }
    promotionStatistic.setPromotionSuccessCount(promotionSuccessCount);
    promotionStatistic.setPromotionFailureCount(promotionFailureCount);
    // set successRate
    Float successRate = 0f;
    if (relativePromotionRecordSize != 0) {
      successRate = (float) promotionSuccessCount / successRate;
    }
    promotionStatistic.setSuccessRate(successRate);
    return promotionStatistic;
  }

  public static PromotionStatistic affectPromotionStatistic(
      PromotionCalculationHelper.AffectPromotionStatisticType type,
      PromotionRecord promotionRecord,
      PromotionStatistic promotionStatistic,
      List<PromotionRecord> relativePromotionRecord) {

    switch (type) {
      case CREATE:
        // append to relativePromotionRecord
        relativePromotionRecord.add(promotionRecord);
      case UPDATE:
        // replace promotionRecord
        Integer replacedId = promotionRecord.getId();
        relativePromotionRecord = relativePromotionRecord.stream().map(rpr -> {
          return rpr.getId() == replacedId ? promotionRecord : rpr;
        }).toList();
      case DELETE:
        // remove promotionRecord
        Integer removedId = promotionRecord.getId();
        relativePromotionRecord = relativePromotionRecord.stream().filter(rpr -> {
          return rpr.getId() != removedId;
        }).toList();
    }

    return affectPromotionStatistic(
        promotionRecord,
        promotionStatistic,
        relativePromotionRecord);
  }
}
