/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.services.PromoterService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.services.PromotionStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PromotionStatistic", description = "PromotionStatistic related operations. Notice that PromotionStatistic is automatically modified by PromotionRecord, hence only GET methods are supported.")
@RestController
@RequestMapping("v1")
public class PromotionStatisticController {

  @Autowired
  private PromotionStatisticService promotionStatisticService;

  @Autowired
  private PromoterService promoterService;

  @Operation(description = "Count promotion statistics by promotion pact name.")
  @GetMapping("/count_promotion_statistic")
  Integer countPromotionStatistics(@RequestParam String promotionPactName) {
    return promotionStatisticService.countByPromotionPactName(promotionPactName);
  }

  @Operation(description = "Get promotion statistics. `promotionPactName` and `promoterName` are optional, but they cannot exist at the same time.")
  @GetMapping("/promotion_statistic")
  List<PromotionStatistic> getPromotionStatistics(
      @RequestParam(required = false) String promotionPactName,
      @RequestParam(required = false) String promoterName) {
    if (promotionPactName != null && promoterName == null) {
      return promotionStatisticService.getPromotionStatisticByPromotionPactName(promotionPactName);
    }
    if (promotionPactName == null && promoterName != null) {
      String email = promoterService
          .getEmailByNickname(promoterName)
          .orElseThrow(
              () -> new ResponseStatusException(
                  HttpStatus.NOT_FOUND, String.format("Promoter %s not found", promoterName)));
      return promotionStatisticService.getPromotionStatisticByPromoterEmail(email);
    }
    return promotionStatisticService.getAllPromotionStatistic();
  }

  @Operation(description = "Get promotion statistics by id.")
  @GetMapping("/promotion_statistic/{id}")
  PromotionStatistic getPromotionStatisticById(@PathVariable Integer id) {
    return promotionStatisticService
        .getPromotionStatistic(id)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("PromotionStatistic %d not found", id)));
  }

}
