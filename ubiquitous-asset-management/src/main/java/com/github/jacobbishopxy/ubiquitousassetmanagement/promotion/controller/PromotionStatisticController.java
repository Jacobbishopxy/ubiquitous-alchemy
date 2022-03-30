/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.controller;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.domain.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto.PromotionStatisticOutput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.service.PromotionStatisticService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.service.PromoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * PromotionStatisticController
 * 
 * PromotionStatistic related operations. Notice that PromotionStatistic is
 * automatically modified by PromotionRecord, hence only GET methods are
 * supported.
 */
@Tag(name = "Promotion")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PROMOTION)
public class PromotionStatisticController {

  @Autowired
  private PromotionStatisticService promotionStatisticService;

  @Autowired
  private PromoterService promoterService;

  @GetMapping("/statistic_count")
  @Operation(description = "Count promotion statistics by promotion pact name.")
  Integer countPromotionStatistics(@RequestParam String promotionPactName) {
    return promotionStatisticService.countByPromotionPactName(promotionPactName);
  }

  @GetMapping("/statistic")
  @Operation(summary = "Get promotion statistics.", description = "`promotionPactName` and `promoterName` are optional, but they cannot exist at the same time.")
  List<PromotionStatisticOutput> getPromotionStatistics(
      @RequestParam(required = false) String promotionPactName,
      @RequestParam(required = false) String promoterName) {
    // initialize the search result
    List<PromotionStatistic> ps;

    if (promotionPactName != null && promoterName == null) {
      // get promotion statistics by promotion pact name
      ps = promotionStatisticService.getPromotionStatisticByPromotionPactName(promotionPactName);
    } else if (promotionPactName == null && promoterName != null) {
      // get promotion statistics by promoter name
      String email = promoterService
          .getEmailByNickname(promoterName)
          .orElseThrow(() -> new ResponseStatusException(
              HttpStatus.NOT_FOUND, String.format("Promoter %s not found", promoterName)));
      ps = promotionStatisticService.getPromotionStatisticByPromoterEmail(email);
    } else {
      // get all promotion statistics
      ps = promotionStatisticService.getAllPromotionStatistic();
    }
    return ps.stream().map(s -> PromotionStatisticOutput.fromPromotionStatistic(s)).toList();
  }

  @GetMapping("/statistic/{id}")
  @Operation(summary = "Get promotion statistics by id.")
  PromotionStatisticOutput getPromotionStatisticById(@PathVariable Integer id) {
    PromotionStatistic ps = promotionStatisticService
        .getPromotionStatistic(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionStatistic %d not found", id)));
    return PromotionStatisticOutput.fromPromotionStatistic(ps);
  }

}
