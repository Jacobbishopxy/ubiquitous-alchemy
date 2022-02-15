/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.repositories.PromotionRecordRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("v1")
public class PromotionRecordController {

  @Autowired
  private PromotionRecordRepo repo;

  @GetMapping("/promotion_record")
  List<PromotionRecord> getPromotionRecords(
      @RequestParam("page") int page,
      @RequestParam("size") int size) {
    return repo.findAll(PageRequest.of(page, size)).getContent();
  }

  @GetMapping("/promotion_record/{id}")
  PromotionRecord getPromotionRecord(@PathVariable Integer id) {
    return repo
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionRecord %s not found", id)));
  }

  @PostMapping("/promotion_record")
  PromotionRecord createPromotionRecord(@RequestBody PromotionRecord promotionRecord) {
    return repo.save(promotionRecord);
  }

  @PutMapping("/promotion_record/{id}")
  PromotionRecord updatePromotionRecord(@PathVariable Integer id, @RequestBody PromotionRecord promotionRecord) {
    return repo
        .findById(id)
        .map(
            record -> {
              record.setPromoter(promotionRecord.getPromoter());
              record.setSymbol(promotionRecord.getSymbol());
              record.setAbbreviation(promotionRecord.getAbbreviation());
              record.setIndustry(promotionRecord.getIndustry());
              record.setDirection(promotionRecord.getDirection());
              record.setOpenTime(promotionRecord.getOpenTime());
              record.setOpenPrice(promotionRecord.getOpenPrice());
              record.setCloseTime(promotionRecord.getCloseTime());
              record.setClosePrice(promotionRecord.getClosePrice());
              record.setEarningsYield(promotionRecord.getEarningsYield());
              record.setScore(promotionRecord.getScore());
              return repo.save(record);
            })
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PromotionRecord %s not found", id)));
  }

  @DeleteMapping("/promotion_record/{id}")
  void deletePromotionRecord(@PathVariable Integer id) {
    repo.deleteById(id);
  }
}
