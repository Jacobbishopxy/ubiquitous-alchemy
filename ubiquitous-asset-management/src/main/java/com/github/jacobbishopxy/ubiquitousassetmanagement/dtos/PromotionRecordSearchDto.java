/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.Optional;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

// TODO: provide a search function
// https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
// https://www.baeldung.com/rest-api-search-language-spring-data-specifications
public record PromotionRecordSearchDto(
    Optional<List<String>> promoters,
    Optional<List<String>> symbols,
    Optional<List<String>> abbreviations,
    Optional<PromotionRecord.Direction> direction,
    Optional<DateRange> openTimeRange,
    Optional<FloatRange> openPriceRange,
    Optional<DateRange> closeTimeRange,
    Optional<FloatRange> closePriceRange,
    Optional<FloatRange> earningsYieldRange,
    Optional<FloatRange> scoreRange,
    Optional<DateRange> createdAtRange,
    Optional<DateRange> updatedAtRange) {
}
