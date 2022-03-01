/**
 * Created by Jacob Xie on 2/24/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.services.helper;

import java.util.Date;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.PromotionConstants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.DateRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.fields.PerformanceScore;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.fields.TradeDirection;

/**
 * PromotionCalculationHelper
 *
 * - calculateEarningsYield
 * - calculateIsArchived
 * - calculatePerformanceScore
 * - affectPromotionStatistic
 */
public class PromotionCalculationHelper {

  public enum AffectPromotionStatisticType {
    CREATE, UPDATE, DELETE
  }

  public static Float calculateEarningsYield(
      TradeDirection direction,
      Float openPrice,
      Float closePrice,
      Float adjustFactor) {
    // make sure none of the parameters are null
    if (openPrice == null || closePrice == null || adjustFactor == null) {
      return null;
    }
    if (direction == TradeDirection.BUY) {
      return closePrice / openPrice * adjustFactor - 1;
    } else {
      return openPrice / closePrice * adjustFactor - 1;
    }
  }

  public static boolean calculateIsArchived(Date closeTime) {
    return closeTime != null;
  }

  public static PerformanceScore calculatePerformanceScore(
      boolean isArchived,
      Float earningsYield) {
    if (earningsYield == null) {
      return null;
    }
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

    // set promoter
    promotionStatistic.setPromoter(promotionRecord.getPromoter());

    // set promotionPact
    promotionStatistic.setPromotionPact(promotionRecord.getPromotionPact());

    // total records length which only include the records in the date range
    DateRange promotionPactDateRange = promotionRecord.getPromotionPact().getDateRange();
    int previousPromotionCount = 0;
    int currentPromotionCount = 0;
    for (PromotionRecord pr : relativePromotionRecord) {
      // if openTime is before promotionPactDateRange's startTime, then count it
      if (promotionPactDateRange.isBefore(pr.getOpenTime())) {
        previousPromotionCount++;
      }
      // if openTime is in promotionPactDateRange, then count it
      if (promotionPactDateRange.inBetween(pr.getOpenTime())) {
        currentPromotionCount++;
      }
    }

    // check MAX_PROMOTION_PER_PROMOTER constraint
    if (currentPromotionCount > PromotionConstants.MAX_PROMOTION_PER_PROMOTER) {
      throw new RuntimeException(
          String.format("MAX_PROMOTION_PER_PROMOTER %d constraint violated",
              PromotionConstants.MAX_PROMOTION_PER_PROMOTER));
    }

    // set previousPromotionCount
    promotionStatistic.setPreviousPromotionCount(previousPromotionCount);

    // set promotionCount
    promotionStatistic.setPromotionCount(currentPromotionCount);

    // set baseScore
    Float baseScore = currentPromotionCount * PromotionConstants.BASE_SCORE_FACTOR;
    promotionStatistic.setBaseScore(baseScore);

    // set performanceScore
    Integer performanceScore = 0;
    Integer pScore = relativePromotionRecord
        .stream()
        .filter(item -> item.getPerformanceScore() != null)
        .mapToInt(item -> item.getPerformanceScore())
        .sum();
    if (pScore != null) {
      performanceScore = pScore;
    }
    promotionStatistic.setPerformanceScore(performanceScore.floatValue());

    // set totalScore
    promotionStatistic.setTotalScore(baseScore + performanceScore);

    // set promotionSuccessCount & promotionFailureCount
    Integer promotionSuccessCount = 0;
    Integer promotionFailureCount = 0;
    for (PromotionRecord pr : relativePromotionRecord) {
      if (pr.getPerformanceScore() != null) {
        if (pr.getPerformanceScore() >= 0) {
          promotionSuccessCount++;
        } else {
          promotionFailureCount++;
        }
      }
    }
    promotionStatistic.setPromotionSuccessCount(promotionSuccessCount);
    promotionStatistic.setPromotionFailureCount(promotionFailureCount);

    // set successRate
    Float successRate = 0f;
    int totalPromotionCount = previousPromotionCount + currentPromotionCount;
    if (totalPromotionCount != 0) {
      successRate = (float) promotionSuccessCount / totalPromotionCount;
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
        break;
      case UPDATE:
        // replace promotionRecord
        Integer replacedId = promotionRecord.getId();
        relativePromotionRecord = relativePromotionRecord.stream().map(rpr -> {
          return rpr.getId() == replacedId ? promotionRecord : rpr;
        }).toList();
        break;
      case DELETE:
        // remove promotionRecord
        Integer removedId = promotionRecord.getId();
        relativePromotionRecord = relativePromotionRecord.stream().filter(rpr -> {
          return rpr.getId() != removedId;
        }).toList();
        break;
    }

    return affectPromotionStatistic(
        promotionRecord,
        promotionStatistic,
        relativePromotionRecord);
  }
}
