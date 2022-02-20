/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.text.ParseException;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.DateRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.IntegerRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordSearch;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.SortDirection;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Direction;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.services.PromotionRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("v1")
public class PromotionRecordController {

  @Autowired
  private PromotionRecordService service;

  @GetMapping("/promotion_record")
  List<PromotionRecord> getPromotionRecords(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam(value = "promoters", required = false) List<String> promoters,
      @RequestParam(value = "symbols", required = false) List<String> symbols,
      @RequestParam(value = "abbreviations", required = false) List<String> abbreviations,
      @RequestParam(value = "industries", required = false) List<String> industries,
      @RequestParam(value = "direction", required = false) Direction direction,
      @RequestParam(value = "openTimeRange", required = false) String openTimeRange,
      @RequestParam(value = "openPriceRange", required = false) String openPriceRange,
      @RequestParam(value = "closeTimeRange", required = false) String closeTimeRange,
      @RequestParam(value = "closePriceRange", required = false) String closePriceRange,
      @RequestParam(value = "earningsYieldRange", required = false) String earningsYieldRange,
      @RequestParam(value = "scoreRange", required = false) String scoreRange,
      @RequestParam(value = "createdAtRange", required = false) String createdAtRange,
      @RequestParam(value = "updatedAtRange", required = false) String updatedAtRange,
      @RequestParam(value = "promoterSort", required = false) String promoterSort,
      @RequestParam(value = "symbolSort", required = false) String symbolSort,
      @RequestParam(value = "industrySort", required = false) String industrySort,
      @RequestParam(value = "directionSort", required = false) String directionSort,
      @RequestParam(value = "openTimeSort", required = false) String openTimeSort,
      @RequestParam(value = "closeTimeSort", required = false) String closeTimeSort,
      @RequestParam(value = "earningsYieldSort", required = false) String earningsYieldSort,
      @RequestParam(value = "scoreSort", required = false) String scoreSort,
      @RequestParam(value = "createdAtSort", required = false) String createdAtSort,
      @RequestParam(value = "updatedAtSort", required = false) String updatedAtSort)
      throws ParseException {

    DateRange otrDto = DateRange.fromString(openTimeRange);
    IntegerRange oprDto = IntegerRange.fromString(openPriceRange);
    DateRange ctrDto = DateRange.fromString(closeTimeRange);
    IntegerRange cprDto = IntegerRange.fromString(closePriceRange);
    IntegerRange eyrDto = IntegerRange.fromString(earningsYieldRange);
    IntegerRange srDto = IntegerRange.fromString(scoreRange);
    DateRange carDto = DateRange.fromString(createdAtRange);
    DateRange uarDto = DateRange.fromString(updatedAtRange);

    SortDirection promoterS = SortDirection.fromString(promoterSort);
    SortDirection symbolS = SortDirection.fromString(symbolSort);
    SortDirection industryS = SortDirection.fromString(industrySort);
    SortDirection directionS = SortDirection.fromString(directionSort);
    SortDirection openTimeS = SortDirection.fromString(openTimeSort);
    SortDirection closeTimeS = SortDirection.fromString(closeTimeSort);
    SortDirection earningsYieldS = SortDirection.fromString(earningsYieldSort);
    SortDirection scoreS = SortDirection.fromString(scoreSort);
    SortDirection createdAtS = SortDirection.fromString(createdAtSort);
    SortDirection updatedAtS = SortDirection.fromString(updatedAtSort);

    PromotionRecordSearch searchDto = new PromotionRecordSearch(
        promoters,
        symbols,
        abbreviations,
        industries,
        direction,
        otrDto,
        oprDto,
        ctrDto,
        cprDto,
        eyrDto,
        srDto,
        carDto,
        uarDto,
        promoterS,
        symbolS,
        industryS,
        directionS,
        openTimeS,
        closeTimeS,
        earningsYieldS,
        scoreS,
        createdAtS,
        updatedAtS);

    if (searchDto.isEmpty()) {
      return service.getPromotionRecords(page, size);
    }

    return service.getPromotionRecords(page, size, searchDto);
  }

  @GetMapping("/promotion_record/{id}")
  PromotionRecord getPromotionRecord(@PathVariable Integer id) {
    return service
        .getPromotionRecord(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionRecord %s not found", id)));
  }

  @PostMapping("/promotion_record")
  PromotionRecord createPromotionRecord(@RequestBody PromotionRecord promotionRecord) {
    return service.createPromotionRecord(promotionRecord);
  }

  @PutMapping("/promotion_record/{id}")
  PromotionRecord updatePromotionRecord(@PathVariable Integer id, @RequestBody PromotionRecord promotionRecord) {
    return service
        .updatePromotionRecord(id, promotionRecord)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionRecord %s not found", id)));
  }

  @DeleteMapping("/promotion_record/{id}")
  void deletePromotionRecord(@PathVariable Integer id) {
    service.deletePromotionRecord(id);
  }
}
