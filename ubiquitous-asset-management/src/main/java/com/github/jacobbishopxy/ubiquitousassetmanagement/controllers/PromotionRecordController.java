/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.util.List;

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
      @RequestParam("size") int size) {
    return service.getPromotionRecords(page, size);
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
