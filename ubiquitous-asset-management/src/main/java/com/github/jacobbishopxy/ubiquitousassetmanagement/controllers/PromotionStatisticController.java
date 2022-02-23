/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.services.PromotionStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("v1")
public class PromotionStatisticController {

  @Autowired
  private PromotionStatisticService service;

  @GetMapping("/count_promotion_statistic_by_name")
  Integer countPromotionStatistics(@RequestParam("name") String name) {
    return service.countByPromotionPactName(name);
  }

  @GetMapping("/promotion_statistic")
  List<PromotionStatistic> getPromotionStatistics() {
    return service.getAllPromotionStatistic();
  }

  @GetMapping("/promotion_statistic/{id}")
  PromotionStatistic getPromotionStatisticById(@PathVariable Integer id) {
    return service
        .getPromotionStatistic(id)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("PromotionStatistic %d not found", id)));
  }

}
