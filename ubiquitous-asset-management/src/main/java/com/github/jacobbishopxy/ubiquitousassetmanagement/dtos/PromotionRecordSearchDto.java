/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

public record PromotionRecordSearchDto(
        List<String> promoters,
        List<String> symbols,
        List<String> abbreviations,
        List<String> industries,
        PromotionRecord.Direction direction,
        DateRange openTimeRange,
        IntegerRange openPriceRange,
        DateRange closeTimeRange,
        IntegerRange closePriceRange,
        IntegerRange earningsYieldRange,
        IntegerRange scoreRange,
        DateRange createdAtRange,
        DateRange updatedAtRange) {

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
                && updatedAtRange == null;
    }
}
