/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordSearchDto;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.repositories.PromotionRecordRepo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.repositories.PromotionRecordSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * PromotionRecordService
 *
 * Business logic for PromotionRecord
 */
@Service
public class PromotionRecordService {

  @Autowired
  private PromotionRecordRepo repo;

  public List<PromotionRecord> getPromotionRecords(int page, int size) {
    return repo.findAll(PageRequest.of(page, size)).getContent();
  }

  public List<PromotionRecord> getPromotionRecords(int page, int size, PromotionRecordSearchDto searchDto) {
    return repo.findAll(new PromotionRecordSpecification(searchDto), PageRequest.of(page, size)).getContent();
  }

  public Optional<PromotionRecord> getPromotionRecord(int id) {
    return repo.findById(id);
  }

  public PromotionRecord createPromotionRecord(PromotionRecord promotionRecord) {
    return repo.save(promotionRecord);
  }

  public Optional<PromotionRecord> updatePromotionRecord(int id, PromotionRecord promotionRecord) {
    return repo.findById(id).map(
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
        });
  }

  public void deletePromotionRecord(int id) {
    repo.deleteById(id);
  }

}
