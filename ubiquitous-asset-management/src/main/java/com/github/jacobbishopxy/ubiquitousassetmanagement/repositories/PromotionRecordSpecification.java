package com.github.jacobbishopxy.ubiquitousassetmanagement.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordSearchDto;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.CriteriaBuilder.In;

public class PromotionRecordSpecification implements Specification<PromotionRecord> {

  private PromotionRecordSearchDto searchDto;

  @Override
  public Predicate toPredicate(
      Root<PromotionRecord> root,
      CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    searchDto.promoters().ifPresent(p -> {
      In<String> inPromoters = criteriaBuilder.in(root.get("promoters"));
      for (String promoter : p) {
        inPromoters.value(promoter);
      }
      predicates.add(inPromoters);
    });

    searchDto.symbols().ifPresent(s -> {
      In<String> inSymbols = criteriaBuilder.in(root.get("symbols"));
      for (String symbol : s) {
        inSymbols.value(symbol);
      }
      predicates.add(inSymbols);
    });

    searchDto.abbreviations().ifPresent(a -> {
      In<String> inAbbreviations = criteriaBuilder.in(root.get("abbreviations"));
      for (String abbreviation : a) {
        inAbbreviations.value(abbreviation);
      }
      predicates.add(inAbbreviations);
    });

    searchDto.direction().ifPresent(d -> {
      predicates.add(criteriaBuilder.equal(root.get("direction"), d));
    });

    searchDto.openTimeRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("openTimeRange"), r.start(), r.end()));
    });

    searchDto.openPriceRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("openPriceRange"), r.start(), r.end()));
    });

    searchDto.closeTimeRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("closeTimeRange"), r.start(), r.end()));
    });

    searchDto.closePriceRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("closePriceRange"), r.start(), r.end()));
    });

    searchDto.earningsYieldRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("earningsYieldRange"), r.start(), r.end()));
    });

    searchDto.scoreRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("scoreRange"), r.start(), r.end()));
    });

    searchDto.createdAtRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("createdAtRange"), r.start(), r.end()));
    });

    searchDto.updatedAtRange().ifPresent(r -> {
      predicates.add(criteriaBuilder.between(root.get("updatedAtRange"), r.start(), r.end()));
    });

    if (predicates.isEmpty()) {
      return null;
    } else {
      return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
    }
  }

}
