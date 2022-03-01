/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.PromotionRecordSearch;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionPact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repositories.PromotionPactRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repositories.PromotionRecordRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repositories.PromotionStatisticRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.services.helper.PromotionCalculationHelper;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.services.specifications.PromotionRecordSpecification;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.repositories.PromoterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PromotionRecordService
 *
 * Business logic for PromotionRecord. Each time a create/update/delete
 * operation is performed, the corresponding PromotionStatistic will be updated
 * as well.
 */
@Service
public class PromotionRecordService {

  @Autowired
  private PromoterRepository pRepo;

  @Autowired
  private PromotionRecordRepository prRepo;

  @Autowired
  private PromotionStatisticRepository psRepo;

  @Autowired
  private PromotionPactRepository ppRepo;

  public List<PromotionRecord> getPromotionRecords(int page, int size, PromotionRecordSearch searchDto) {

    if (searchDto == null) {
      return prRepo.findAll(PageRequest.of(page, size)).getContent();
    }

    // since searching promoters is based on nickname, we need to convert to
    // promoter's email
    if (searchDto.promoters() != null) {
      List<String> promoterEmails = new ArrayList<>();
      promoterEmails = pRepo.findEmailsByNicknameIn(searchDto.promoters());
      searchDto = PromotionRecordSearch.replacePromoterNamesByPromoterEmails(searchDto, promoterEmails);
    }

    PromotionRecordSpecification prs = new PromotionRecordSpecification(searchDto);

    List<Sort.Order> orders = searchDto.getOrders();
    PageRequest pr = orders.isEmpty() ? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(orders));

    return prRepo.findAll(prs, pr).getContent();
  }

  public Optional<PromotionRecord> getPromotionRecord(int id) {
    return prRepo.findById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public PromotionRecord createPromotionRecord(PromotionRecord promotionRecord) {
    String promotionPactName = promotionRecord.getPromotionPact().getName();
    String promoterEmail = promotionRecord.getPromoter().getEmail();

    // 0. set promotionRecord's promotionPact since it is null by default
    PromotionPact promotionPact = this.ppRepo.findByName(promotionPactName)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("PromotionPact %s does not exist", promotionPactName)));
    promotionRecord.setPromotionPact(promotionPact);

    // 1. fetch promotion statistic, create one if not exist
    PromotionStatistic promotionStatistic = this.psRepo
        .findByPromotionPactNameAndPromoterEmail(promotionPactName, promoterEmail)
        .orElse(new PromotionStatistic());

    // 2. fetch all promotion records by promotion pact name and promoter email
    List<PromotionRecord> relativePromotionRecord = this.prRepo
        .findByPromotionPactNameAndPromoterEmail(promotionPactName, promoterEmail);

    // 3. calculation
    promotionStatistic = PromotionCalculationHelper.affectPromotionStatistic(
        PromotionCalculationHelper.AffectPromotionStatisticType.CREATE,
        promotionRecord,
        promotionStatistic,
        relativePromotionRecord);

    // 4. save to promotion statistic
    this.psRepo.save(promotionStatistic);

    // 5. create promotion record
    return prRepo.save(promotionRecord);
  }

  @Transactional(rollbackFor = Exception.class)
  public Optional<PromotionRecord> updatePromotionRecord(int id, PromotionRecord promotionRecord) {
    String promotionPactName = promotionRecord.getPromotionPact().getName();
    String promoterEmail = promotionRecord.getPromoter().getEmail();

    // 0. set promotionRecord's id & promotionPact since it is null by default
    PromotionPact promotionPact = this.ppRepo.findByName(promotionPactName)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("PromotionPact %s does not exist", promotionPactName)));
    promotionRecord.setId(id);
    promotionRecord.setPromotionPact(promotionPact);

    // 1. fetch promotion statistic, create one if not exist
    PromotionStatistic promotionStatistic = this.psRepo
        .findByPromotionPactNameAndPromoterEmail(promotionPactName, promoterEmail)
        .orElse(new PromotionStatistic());

    // 2. fetch all promotion records by promotion pact name and promoter email
    List<PromotionRecord> relativePromotionRecord = this.prRepo
        .findByPromotionPactNameAndPromoterEmail(promotionPactName, promoterEmail);

    // 3. calculation
    promotionStatistic = PromotionCalculationHelper.affectPromotionStatistic(
        PromotionCalculationHelper.AffectPromotionStatisticType.UPDATE,
        promotionRecord,
        promotionStatistic,
        relativePromotionRecord);

    // 4. save to promotion statistic
    this.psRepo.save(promotionStatistic);

    // 5. update promotion record
    return prRepo.findById(id).map(
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
          record.setAdjustFactorChange(promotionRecord.getAdjustFactorChange());
          record.setPerformanceScore(promotionRecord.getPerformanceScore());
          record.setPromotionPact(promotionRecord.getPromotionPact());
          record.setIsArchived(promotionRecord.getIsArchived());
          // calculate earnings yield
          record.setEarningsYield();
          return prRepo.save(record);
        });
  }

  @Transactional(rollbackFor = Exception.class)
  public void deletePromotionRecord(int id) {
    PromotionRecord promotionRecord = prRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("PromotionRecord %d not found", id)));

    String promotionPactName = promotionRecord.getPromotionPact().getName();
    String promoterEmail = promotionRecord.getPromoter().getEmail();

    // 0. set promotionRecord's id & promotionPact since it is null by default
    PromotionPact promotionPact = this.ppRepo.findByName(promotionPactName)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("PromotionPact %s does not exist", promotionPactName)));
    promotionRecord.setId(id);
    promotionRecord.setPromotionPact(promotionPact);

    // 1. fetch promotion statistic, create one if not exist
    PromotionStatistic promotionStatistic = this.psRepo
        .findByPromotionPactNameAndPromoterEmail(promotionPactName, promoterEmail)
        .orElse(new PromotionStatistic());

    // 2. fetch all promotion records by promotion pact name and promoter email
    List<PromotionRecord> relativePromotionRecord = this.prRepo
        .findByPromotionPactNameAndPromoterEmail(promotionPactName, promoterEmail);

    // 3. calculation
    promotionStatistic = PromotionCalculationHelper.affectPromotionStatistic(
        PromotionCalculationHelper.AffectPromotionStatisticType.DELETE,
        promotionRecord,
        promotionStatistic,
        relativePromotionRecord);

    // 4. save to promotion statistic
    this.psRepo.save(promotionStatistic);

    // 5. delete promotion record
    prRepo.deleteById(id);
  }

  public long countPromotionRecords() {
    return prRepo.count();
  }

  public long countPromotionRecordsByPromotionPactName(String promotionPactName) {
    return prRepo.countByPromotionPactName(promotionPactName);
  }
}
