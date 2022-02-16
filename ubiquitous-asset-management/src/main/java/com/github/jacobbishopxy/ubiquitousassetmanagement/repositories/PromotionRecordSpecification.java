package com.github.jacobbishopxy.ubiquitousassetmanagement.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.DateRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.IntegerRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordSearchDto;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.CriteriaBuilder.In;

public class PromotionRecordSpecification implements Specification<PromotionRecord> {

  private PromotionRecordSearchDto searchDto;

  public PromotionRecordSpecification(PromotionRecordSearchDto searchDto) {
    this.searchDto = searchDto;
  }

  @Override
  public Predicate toPredicate(
      Root<PromotionRecord> root,
      CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    List<String> promoters = searchDto.promoters();
    if (promoters != null) {
      In<String> inPromoters = criteriaBuilder.in(root.get("promoter"));
      for (String promoter : promoters) {
        inPromoters.value(promoter);
      }
      predicates.add(inPromoters);
    }

    List<String> symbols = searchDto.symbols();
    if (symbols != null) {
      In<String> inSymbols = criteriaBuilder.in(root.get("symbol"));
      for (String symbol : symbols) {
        inSymbols.value(symbol);
      }
      predicates.add(inSymbols);
    }

    List<String> abbreviations = searchDto.abbreviations();
    if (abbreviations != null) {
      In<String> inAbbreviations = criteriaBuilder.in(root.get("abbreviation"));
      for (String abbreviation : abbreviations) {
        inAbbreviations.value(abbreviation);
      }
      predicates.add(inAbbreviations);
    }

    PromotionRecord.Direction direction = searchDto.direction();
    if (direction != null) {
      predicates.add(criteriaBuilder.equal(root.get("direction"), direction));
    }

    DateRange openTimeRange = searchDto.openTimeRange();
    if (openTimeRange != null) {
      predicates.add(criteriaBuilder.between(root.get("openTime"), openTimeRange.start(), openTimeRange.end()));
    }

    IntegerRange openPriceRange = searchDto.openPriceRange();
    if (openPriceRange != null) {
      predicates.add(criteriaBuilder.between(root.get("openPrice"), openPriceRange.start(), openPriceRange.end()));
    }

    DateRange closeTimeRange = searchDto.closeTimeRange();
    if (closeTimeRange != null) {
      predicates.add(criteriaBuilder.between(root.get("closeTime"), closeTimeRange.start(), closeTimeRange.end()));
    }

    IntegerRange closePriceRange = searchDto.closePriceRange();
    if (closePriceRange != null) {
      predicates.add(criteriaBuilder.between(root.get("closePrice"), closePriceRange.start(), closePriceRange.end()));
    }

    IntegerRange earningsYieldRange = searchDto.earningsYieldRange();
    if (earningsYieldRange != null) {
      predicates.add(
          criteriaBuilder.between(root.get("earningsYield"), earningsYieldRange.start(), earningsYieldRange.end()));
    }

    IntegerRange scoreRange = searchDto.scoreRange();
    if (scoreRange != null) {
      predicates.add(criteriaBuilder.between(root.get("score"), scoreRange.start(), scoreRange.end()));
    }

    DateRange createdAtRange = searchDto.createdAtRange();
    if (createdAtRange != null) {
      predicates.add(criteriaBuilder.between(root.get("createdAt"), createdAtRange.start(), createdAtRange.end()));
    }

    DateRange updatedAtRange = searchDto.updatedAtRange();
    if (updatedAtRange != null) {
      predicates.add(criteriaBuilder.between(root.get("updatedAt"), updatedAtRange.start(), updatedAtRange.end()));
    }

    if (predicates.isEmpty()) {
      return null;
    } else {
      return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
    }
  }

}
