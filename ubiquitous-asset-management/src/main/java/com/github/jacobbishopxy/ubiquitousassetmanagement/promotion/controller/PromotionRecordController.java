/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.controller;

import java.text.ParseException;
import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.domain.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.domain.obj.TradeDirection;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto.DateRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto.IntegerRange;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto.PromotionRecordInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto.PromotionRecordOutput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto.PromotionRecordSearch;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto.SortDirection;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.service.PromotionPactService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.service.PromotionRecordService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.service.PromoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Promotion")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PROMOTION)
public class PromotionRecordController {

  @Autowired
  private PromoterService promoterService;

  @Autowired
  private PromotionPactService promotionPactService;

  @Autowired
  private PromotionRecordService promotionRecordService;

  @GetMapping("/record_count")
  @Operation(summary = "Count promotion records by promotion pact name.")
  long countPromotionRecords(@RequestParam String promotionPactName) {
    return promotionRecordService
        .countPromotionRecordsByPromotionPactName(promotionPactName);
  }

  @GetMapping("/record")
  @Operation(summary = "Get promotion records by page and size.", description = "Searching parameters are optional.")
  List<PromotionRecordOutput> getPromotionRecords(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam(value = "promoters", required = false) List<String> promoters,
      @RequestParam(value = "symbols", required = false) List<String> symbols,
      @RequestParam(value = "abbreviations", required = false) List<String> abbreviations,
      @RequestParam(value = "industries", required = false) List<String> industries,
      @RequestParam(value = "direction", required = false) TradeDirection direction,
      @RequestParam(value = "open_time_range", required = false) String openTimeRange,
      @RequestParam(value = "open_price_range", required = false) String openPriceRange,
      @RequestParam(value = "close_time_range", required = false) String closeTimeRange,
      @RequestParam(value = "close_price_range", required = false) String closePriceRange,
      @RequestParam(value = "earnings_yield_range", required = false) String earningsYieldRange,
      @RequestParam(value = "score_range", required = false) String scoreRange,
      @RequestParam(value = "promotion_pact_names", required = false) List<String> promotionPactNames,
      @RequestParam(value = "is_archived", required = false) Boolean isArchived,
      @RequestParam(value = "created_at_range", required = false) String createdAtRange,
      @RequestParam(value = "updated_at_range", required = false) String updatedAtRange,
      @RequestParam(value = "promoter_sort", required = false) String promoterSort,
      @RequestParam(value = "symbol_sort", required = false) String symbolSort,
      @RequestParam(value = "industry_sort", required = false) String industrySort,
      @RequestParam(value = "direction_sort", required = false) String directionSort,
      @RequestParam(value = "open_time_sort", required = false) String openTimeSort,
      @RequestParam(value = "close_time_sort", required = false) String closeTimeSort,
      @RequestParam(value = "earnings_yield_sort", required = false) String earningsYieldSort,
      @RequestParam(value = "score_sort", required = false) String scoreSort,
      @RequestParam(value = "created_at_sort", required = false) String createdAtSort,
      @RequestParam(value = "updated_at_sort", required = false) String updatedAtSort)
      throws ParseException {

    // TODO:
    // replace all nullable requestParam by `PromotionRecordSearch`

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

  @GetMapping("/record/{id}")
  @Operation(summary = "Get promotion record by id.")
  PromotionRecordOutput getPromotionRecord(@PathVariable Integer id) {
    PromotionRecord pr = promotionRecordService
        .getPromotionRecord(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionRecord %s not found", id)));
    return PromotionRecordOutput.fromPromotionRecord(pr);
  }

  @PostMapping("/record")
  @Operation(summary = "Create promotion record.", description = "Noticed that this method will also effect the promotion statistic automatically.")
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

  @PutMapping("/record/{id}")
  @Operation(summary = "Update promotion record.", description = "Noticed that this method will also effect the promotion statistic automatically.")
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

  @DeleteMapping("/record/{id}")
  @Operation(summary = "Delete promotion record.", description = "Noticed that this method will also effect the promotion statistic automatically.")
  void deletePromotionRecord(@PathVariable Integer id) {
    promotionRecordService.deletePromotionRecord(id);
  }

}
