/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.Optional;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

// TODO: provide a search function
// https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
public record PromotionRecordSearchDto(
        Optional<List<String>> promoters,
        Optional<List<String>> symbols,
        Optional<List<String>> abbreviations,
        Optional<PromotionRecord.Direction> direction,
        Optional<DateRange> openTimeRange,
        Optional<PriceRange> openPriceRange,
        Optional<DateRange> closeTimeRange,
        Optional<PriceRange> closePriceRange,
        Optional<Float> earningsYield,
        Optional<Float> score,
        Optional<DateRange> createdAtRange,
        Optional<DateRange> updatedAtRange) {
}
