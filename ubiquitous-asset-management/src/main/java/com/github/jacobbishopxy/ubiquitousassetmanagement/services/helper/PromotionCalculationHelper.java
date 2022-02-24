/**
 * Created by Jacob Xie on 2/24/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services.helper;

import java.util.Date;

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
}
