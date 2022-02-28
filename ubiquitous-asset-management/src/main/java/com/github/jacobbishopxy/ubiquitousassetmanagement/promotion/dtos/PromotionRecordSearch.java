/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos;

import java.util.ArrayList;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.fields.TradeDirection;

import org.springframework.data.domain.Sort.Order;

/**
 * PromotionRecordSearchDto
 *
 * promoters
 * symbols
 * abbreviations
 * industries
 * direction
 * openTimeRange
 * openPriceRange
 * closeTimeRange
 * closePriceRange
 * earningsYieldRange
 * scoreRange
 * promotionPactNames
 * isArchived
 * createdAtRange
 * updatedAtRange
 * promoterSort
 * symbolSort
 * industrySort
 * directionSort
 * openTimeSort
 * closeTimeSort
 * earningsYieldSort
 * scoreSort
 * createdAtSort
 * updateAtSort
 */
public record PromotionRecordSearch(
        List<String> promoters,
        List<String> symbols,
        List<String> abbreviations,
        List<String> industries,
        TradeDirection direction,
        DateRange openTimeRange,
        IntegerRange openPriceRange,
        DateRange closeTimeRange,
        IntegerRange closePriceRange,
        IntegerRange earningsYieldRange,
        IntegerRange scoreRange,
        List<String> promotionPactNames,
        Boolean isArchived,
        DateRange createdAtRange,
        DateRange updatedAtRange,
        SortDirection promoterSort,
        SortDirection symbolSort,
        SortDirection industrySort,
        SortDirection directionSort,
        SortDirection openTimeSort,
        SortDirection closeTimeSort,
        SortDirection earningsYieldSort,
        SortDirection scoreSort,
        SortDirection createdAtSort,
        SortDirection updateAtSort) {

    public boolean isEmpty() {
        return promoters == null
                && symbols == null
                && abbreviations == null
                && direction == null
                && openTimeRange == null
                && openPriceRange == null
                && closeTimeRange == null
                && closePriceRange == null
                && earningsYieldRange == null
                && scoreRange == null
                && promotionPactNames == null
                && isArchived == null
                && createdAtRange == null
                && updatedAtRange == null
                && promoterSort == null
                && symbolSort == null
                && industrySort == null
                && directionSort == null
                && openTimeSort == null
                && closeTimeSort == null
                && earningsYieldSort == null
                && scoreSort == null
                && createdAtSort == null
                && updateAtSort == null;
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<Order>();

        if (promoterSort != null) {
            orders.add(promoterSort.getOrder("promoter"));
        }
        if (symbolSort != null) {
            orders.add(symbolSort.getOrder("symbol"));
        }
        if (industrySort != null) {
            orders.add(industrySort.getOrder("industry"));
        }
        if (directionSort != null) {
            orders.add(directionSort.getOrder("direction"));
        }
        if (openTimeSort != null) {
            orders.add(openTimeSort.getOrder("openTime"));
        }
        if (closeTimeSort != null) {
            orders.add(closeTimeSort.getOrder("closeTime"));
        }
        if (earningsYieldSort != null) {
            orders.add(earningsYieldSort.getOrder("earningsYield"));
        }
        if (scoreSort != null) {
            orders.add(scoreSort.getOrder("score"));
        }
        if (createdAtSort != null) {
            orders.add(createdAtSort.getOrder("createdAt"));
        }
        if (updateAtSort != null) {
            orders.add(updateAtSort.getOrder("updatedAt"));
        }

        return orders;
    }

    public static PromotionRecordSearch replacePromoterNamesByPromoterEmails(
            PromotionRecordSearch promotionRecordSearch,
            List<String> promoterEmails) {

        return new PromotionRecordSearch(
                promoterEmails,
                promotionRecordSearch.symbols,
                promotionRecordSearch.abbreviations,
                promotionRecordSearch.industries,
                promotionRecordSearch.direction,
                promotionRecordSearch.openTimeRange,
                promotionRecordSearch.openPriceRange,
                promotionRecordSearch.closeTimeRange,
                promotionRecordSearch.closePriceRange,
                promotionRecordSearch.earningsYieldRange,
                promotionRecordSearch.scoreRange,
                promotionRecordSearch.promotionPactNames,
                promotionRecordSearch.isArchived,
                promotionRecordSearch.createdAtRange,
                promotionRecordSearch.updatedAtRange,
                promotionRecordSearch.promoterSort,
                promotionRecordSearch.symbolSort,
                promotionRecordSearch.industrySort,
                promotionRecordSearch.directionSort,
                promotionRecordSearch.openTimeSort,
                promotionRecordSearch.closeTimeSort,
                promotionRecordSearch.earningsYieldSort,
                promotionRecordSearch.scoreSort,
                promotionRecordSearch.createdAtSort,
                promotionRecordSearch.updateAtSort);
    }

}
