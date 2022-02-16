/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.text.ParseException;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.DateRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.IntegerRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordSearchDto;
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
      @RequestParam(value = "direction", required = false) PromotionRecord.Direction direction,
      @RequestParam(value = "openTimeRange", required = false) String openTimeRange,
      @RequestParam(value = "openPriceRange", required = false) String openPriceRange,
      @RequestParam(value = "closeTimeRange", required = false) String closeTimeRange,
      @RequestParam(value = "closePriceRange", required = false) String closePriceRange,
      @RequestParam(value = "earningsYieldRange", required = false) String earningsYieldRange,
      @RequestParam(value = "scoreRange", required = false) String scoreRange,
      @RequestParam(value = "createdAtRange", required = false) String createdAtRange,
      @RequestParam(value = "updatedAtRange", required = false) String updatedAtRange) throws ParseException {

    DateRange otrDto = openTimeRange != null ? DateRange.fromString(openTimeRange) : null;
    IntegerRange oprDto = openPriceRange != null ? IntegerRange.fromString(openPriceRange) : null;
    DateRange ctrDto = closePriceRange != null ? DateRange.fromString(closeTimeRange) : null;
    IntegerRange cprDto = closePriceRange != null ? IntegerRange.fromString(closePriceRange) : null;
    IntegerRange eyrDto = earningsYieldRange != null ? IntegerRange.fromString(earningsYieldRange) : null;
    IntegerRange srDto = scoreRange != null ? IntegerRange.fromString(scoreRange) : null;
    DateRange carDto = createdAtRange != null ? DateRange.fromString(createdAtRange) : null;
    DateRange uarDto = updatedAtRange != null ? DateRange.fromString(updatedAtRange) : null;

    PromotionRecordSearchDto searchDto = new PromotionRecordSearchDto(
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
        uarDto);

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
