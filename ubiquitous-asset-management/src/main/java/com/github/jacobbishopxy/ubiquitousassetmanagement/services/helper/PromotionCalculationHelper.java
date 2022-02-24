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
 */
public class PromotionCalculationHelper {

  static Float baseScoreFactor = 2.5f;

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

  // TODO:
  // these methods can be combined into one
  private static PromotionStatistic affectPromotionStatistic(
      PromotionRecord promotionRecord,
      PromotionStatistic promotionStatistic,
      List<PromotionRecord> relativePromotionRecord) {

    // length of relativePromotionRecord
    int relativePromotionRecordSize = relativePromotionRecord.size();
    // set promoter
    promotionStatistic.setPromoter(promotionRecord.getPromoter());
    // set promotionCount
    Integer promotionCount = relativePromotionRecordSize;
    promotionStatistic.setPromotionCount(promotionCount);
    // set baseScore
    Float baseScore = relativePromotionRecordSize * PromotionCalculationHelper.baseScoreFactor;
    promotionStatistic.setBaseScore(baseScore);
    // set performanceScore
    Integer performanceScore = relativePromotionRecord
        .stream()
        .filter(item -> item.getPerformanceScore() != null)
        .mapToInt(item -> item.getPerformanceScore())
        .sum();
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
    Float successRate = (float) promotionSuccessCount / promotionCount;
    promotionStatistic.setSuccessRate(successRate);
    return promotionStatistic;
  }

  public static PromotionStatistic affectPromotionStatisticByCreate(
      PromotionRecord promotionRecord,
      PromotionStatistic promotionStatistic,
      List<PromotionRecord> relativePromotionRecord) {

    // append to relativePromotionRecord
    relativePromotionRecord.add(promotionRecord);

    return affectPromotionStatistic(promotionRecord, promotionStatistic, relativePromotionRecord);
  }

  public static PromotionStatistic affectPromotionStatisticByUpdate(
      PromotionRecord promotionRecord,
      PromotionStatistic promotionStatistic,
      List<PromotionRecord> relativePromotionRecord) {

    // replace promotionRecord
    Integer prId = promotionRecord.getId();
    relativePromotionRecord = relativePromotionRecord.stream().map(rpr -> {
      return rpr.getId() == prId ? promotionRecord : rpr;
    }).toList();

    return affectPromotionStatistic(promotionRecord, promotionStatistic, relativePromotionRecord);
  }

  public static PromotionStatistic affectPromotionStatisticByDelete(
      PromotionRecord promotionRecord,
      PromotionStatistic promotionStatistic,
      List<PromotionRecord> relativePromotionRecord) {

    // remove promotionRecord
    Integer prId = promotionRecord.getId();
    relativePromotionRecord = relativePromotionRecord.stream().filter(rpr -> {
      return rpr.getId() != prId;
    }).toList();

    return affectPromotionStatistic(promotionRecord, promotionStatistic, relativePromotionRecord);
  }
}
