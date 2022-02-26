/**
 * Created by Jacob Xie on 2/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services.specifications;

import java.util.ArrayList;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.DateRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.IntegerRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordSearch;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.fields.TradeDirection;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;

/**
 * PromotionRecordSpecification
 * 
 * Used to filter PromotionRecord.
 */
public class PromotionRecordSpecification implements Specification<PromotionRecord> {

  private PromotionRecordSearch searchDto;

  public PromotionRecordSpecification(PromotionRecordSearch searchDto) {
    this.searchDto = searchDto;
  }

  @Override
  public Predicate toPredicate(
      Root<PromotionRecord> root,
      CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    // search by promoters' email
    List<String> promoters = searchDto.promoters();
    if (promoters != null) {
      In<Promoter> inPromoters = criteriaBuilder.in(root.get("promoter"));
      for (String promoter : promoters) {
        Promoter p = new Promoter();
        p.setEmail(promoter);
        inPromoters.value(p);
      }
      predicates.add(inPromoters);
    }

    // search by symbols
    List<String> symbols = searchDto.symbols();
    if (symbols != null) {
      In<String> inSymbols = criteriaBuilder.in(root.get("symbol"));
      for (String symbol : symbols) {
        inSymbols.value(symbol);
      }
      predicates.add(inSymbols);
    }

    // search by abbreviations
    List<String> abbreviations = searchDto.abbreviations();
    if (abbreviations != null) {
      In<String> inAbbreviations = criteriaBuilder.in(root.get("abbreviation"));
      for (String abbreviation : abbreviations) {
        inAbbreviations.value(abbreviation);
      }
      predicates.add(inAbbreviations);
    }

    // search by industries
    List<String> industries = searchDto.industries();
    if (industries != null) {
      In<String> inIndustries = criteriaBuilder.in(root.get("industry"));
      for (String industry : industries) {
        inIndustries.value(industry);
      }
      predicates.add(inIndustries);
    }

    // search by direction
    TradeDirection direction = searchDto.direction();
    if (direction != null) {
      predicates.add(criteriaBuilder.equal(root.get("direction"), direction));
    }

    // search by open time range
    DateRange openTimeRange = searchDto.openTimeRange();
    if (openTimeRange != null) {
      predicates.add(criteriaBuilder.between(root.get("openTime"), openTimeRange.start(), openTimeRange.end()));
    }

    // search by open price range
    IntegerRange openPriceRange = searchDto.openPriceRange();
    if (openPriceRange != null) {
      predicates.add(criteriaBuilder.between(root.get("openPrice"), openPriceRange.start(), openPriceRange.end()));
    }

    // search by close time range
    DateRange closeTimeRange = searchDto.closeTimeRange();
    if (closeTimeRange != null) {
      predicates.add(criteriaBuilder.between(root.get("closeTime"), closeTimeRange.start(), closeTimeRange.end()));
    }

    // search by close price range
    IntegerRange closePriceRange = searchDto.closePriceRange();
    if (closePriceRange != null) {
      predicates.add(criteriaBuilder.between(root.get("closePrice"), closePriceRange.start(), closePriceRange.end()));
    }

    // search by earnings yield range
    IntegerRange earningsYieldRange = searchDto.earningsYieldRange();
    if (earningsYieldRange != null) {
      predicates.add(
          criteriaBuilder.between(root.get("earningsYield"), earningsYieldRange.start(), earningsYieldRange.end()));
    }

    // search by score range
    IntegerRange scoreRange = searchDto.scoreRange();
    if (scoreRange != null) {
      predicates.add(criteriaBuilder.between(root.get("score"), scoreRange.start(), scoreRange.end()));
    }

    List<String> promotionPactNames = searchDto.promotionPactNames();
    if (promotionPactNames != null) {
      In<String> inPromotionPactNames = criteriaBuilder.in(root.get("promotionPactName"));
      for (String promotionPactName : promotionPactNames) {
        inPromotionPactNames.value(promotionPactName);
      }
      predicates.add(inPromotionPactNames);
    }

    Boolean isArchived = searchDto.isArchived();
    if (isArchived != null) {
      predicates.add(criteriaBuilder.equal(root.get("isArchived"), isArchived));
    }

    // search by createdAt range
    DateRange createdAtRange = searchDto.createdAtRange();
    if (createdAtRange != null) {
      predicates.add(criteriaBuilder.between(root.get("createdAt"), createdAtRange.start(), createdAtRange.end()));
    }

    // search by updatedAt range
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
