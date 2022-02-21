/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.ArrayList;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Direction;

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
        Direction direction,
        DateRange openTimeRange,
        IntegerRange openPriceRange,
        DateRange closeTimeRange,
        IntegerRange closePriceRange,
        IntegerRange earningsYieldRange,
        IntegerRange scoreRange,
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
}
