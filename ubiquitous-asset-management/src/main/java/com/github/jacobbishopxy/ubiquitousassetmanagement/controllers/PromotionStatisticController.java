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

@RestController
@RequestMapping("v1")
public class PromotionStatisticController {

  @Autowired
  private PromotionStatisticService promotionStatisticService;

  @Autowired
  private PromoterService promoterService;

  @GetMapping("/count_promotion_statistic_by_name")
  Integer countPromotionStatistics(@RequestParam("name") String name) {
    return promotionStatisticService.countByPromotionPactName(name);
  }

  @GetMapping("/promotion_statistic")
  List<PromotionStatistic> getPromotionStatistics(
      @RequestParam(name = "pactName", required = false) String pactName,
      @RequestParam(name = "promoterName", required = false) String promoterName) {
    if (pactName != null && promoterName == null) {
      return promotionStatisticService
          .getPromotionStatisticByPromotionPactName(pactName)
          .orElseThrow(
              () -> new ResponseStatusException(
                  HttpStatus.NOT_FOUND, String.format("PromotionPact %s not found", pactName)));
    }
    if (pactName == null && promoterName != null) {
      String email = promoterService
          .getEmailByNickname(promoterName)
          .orElseThrow(
              () -> new ResponseStatusException(
                  HttpStatus.NOT_FOUND, String.format("Promoter %s not found", promoterName)));
      return promotionStatisticService
          .getPromotionStatisticByPromoterEmail(email)
          .orElseThrow(
              () -> new ResponseStatusException(
                  HttpStatus.NOT_FOUND, String.format("Promoter %s not found", promoterName)));
    }
    return promotionStatisticService.getAllPromotionStatistic();
  }

  @GetMapping("/promotion_statistic/{id}")
  PromotionStatistic getPromotionStatisticById(@PathVariable Integer id) {
    return promotionStatisticService
        .getPromotionStatistic(id)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("PromotionStatistic %d not found", id)));
  }

}
