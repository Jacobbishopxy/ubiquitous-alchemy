/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.PromotionRecordSearch;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.repositories.PromotionRecordRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.specifications.PromotionRecordSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

/**
 * PromotionRecordService
 *
 * Business logic for PromotionRecord
 */
@Service
public class PromotionRecordService {

  @Autowired
  private PromotionRecordRepository repo;

  public List<PromotionRecord> getPromotionRecords(int page, int size, PromotionRecordSearch searchDto) {

    if (searchDto == null) {
      return repo.findAll(PageRequest.of(page, size)).getContent();
    }

    PromotionRecordSpecification prs = new PromotionRecordSpecification(searchDto);

    List<Order> orders = searchDto.getOrders();
    PageRequest pr = orders.isEmpty() ? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(orders));

    return repo.findAll(prs, pr).getContent();
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

  public long countPromotionRecords() {
    return repo.count();
  }

}
