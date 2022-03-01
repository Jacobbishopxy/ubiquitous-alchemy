/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.controllers;

import java.text.ParseException;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.DateRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.IntegerRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.PromotionRecordInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.PromotionRecordOutput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.PromotionRecordSearch;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.SortDirection;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.fields.TradeDirection;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.services.PromotionPactService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.services.PromotionRecordService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services.PromoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PromotionRecord", description = "PromotionRecord related operations")
@RestController
@RequestMapping("v1")
public class PromotionRecordController {

  @Autowired
  private PromoterService promoterService;

  @Autowired
  private PromotionPactService promotionPactService;

  @Autowired
  private PromotionRecordService promotionRecordService;

  @Operation(description = "Count promotion records by promotion pact name.")
  @GetMapping("/count_promotion_record")
  long countPromotionRecords(@RequestParam String promotionPactName) {
    return promotionRecordService
        .countPromotionRecordsByPromotionPactName(promotionPactName);
  }

  @Operation(description = "Get promotion records by page and size. Searching parameters are optional.")
  @GetMapping("/promotion_record")
  List<PromotionRecordOutput> getPromotionRecords(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam(value = "promoters", required = false) List<String> promoters,
      @RequestParam(value = "symbols", required = false) List<String> symbols,
      @RequestParam(value = "abbreviations", required = false) List<String> abbreviations,
      @RequestParam(value = "industries", required = false) List<String> industries,
      @RequestParam(value = "direction", required = false) TradeDirection direction,
      @RequestParam(value = "openTimeRange", required = false) String openTimeRange,
      @RequestParam(value = "openPriceRange", required = false) String openPriceRange,
      @RequestParam(value = "closeTimeRange", required = false) String closeTimeRange,
      @RequestParam(value = "closePriceRange", required = false) String closePriceRange,
      @RequestParam(value = "earningsYieldRange", required = false) String earningsYieldRange,
      @RequestParam(value = "scoreRange", required = false) String scoreRange,
      @RequestParam(value = "promotionPactNames", required = false) List<String> promotionPactNames,
      @RequestParam(value = "isArchived", required = false) Boolean isArchived,
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
        promotionPactNames,
        isArchived,
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

    searchDto = searchDto.isEmpty() ? null : searchDto;

    return promotionRecordService
        .getPromotionRecords(page, size, searchDto)
        .stream()
        .map(pr -> PromotionRecordOutput.fromPromotionRecord(pr))
        .toList();
  }

  @Operation(description = "Get promotion record by id.")
  @GetMapping("/promotion_record/{id}")
  PromotionRecordOutput getPromotionRecord(@PathVariable Integer id) {
    PromotionRecord pr = promotionRecordService
        .getPromotionRecord(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionRecord %s not found", id)));
    return PromotionRecordOutput.fromPromotionRecord(pr);
  }

  @Operation(description = "Create promotion record. Noticed that this method will also effect the promotion statistic automatically.")
  @PostMapping("/promotion_record")
  PromotionRecordOutput createPromotionRecord(@RequestBody PromotionRecordInput dto) {
    String email = promoterService
        .getEmailByNickname(dto.promoter())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST, String.format("Promoter %s not found", dto.promoter())));

    String pactName = promotionPactService
        .getPromotionPact(dto.promotionPactName())
        .map(pact -> pact.getName())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST, String.format("PromotionPact %s not found", dto.promotionPactName())));

    PromotionRecord pr = PromotionRecord.fromPromotionRecordDto(dto, pactName, email);

    pr = promotionRecordService.createPromotionRecord(pr);

    return PromotionRecordOutput.fromPromotionRecord(pr, pactName, dto.promoter());
  }

  @Operation(description = "Update promotion record. Noticed that this method will also effect the promotion statistic automatically.")
  @PutMapping("/promotion_record/{id}")
  PromotionRecordOutput updatePromotionRecord(@PathVariable Integer id, @RequestBody PromotionRecordInput dto) {
    String email = promoterService.getEmailByNickname(dto.promoter())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST, String.format("Promoter %s not found", dto.promoter())));

    String pactName = promotionPactService
        .getPromotionPact(dto.promotionPactName())
        .map(pact -> pact.getName())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST, String.format("PromotionPact %s not found", dto.promotionPactName())));

    PromotionRecord pr = PromotionRecord.fromPromotionRecordDto(dto, pactName, email);

    pr = promotionRecordService
        .updatePromotionRecord(id, pr)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionRecord %s not found", id)));

    return PromotionRecordOutput.fromPromotionRecord(pr, pactName, dto.promoter());
  }

  @Operation(description = "Delete promotion record. Noticed that this method will also effect the promotion statistic automatically.")
  @DeleteMapping("/promotion_record/{id}")
  void deletePromotionRecord(@PathVariable Integer id) {
    promotionRecordService.deletePromotionRecord(id);
  }

}
