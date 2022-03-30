/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.service;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.domain.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repository.PromotionStatisticRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PromotionStatisticService
 * 
 * PromotionStatisticService is used to manage promotion statistics.
 */
@Service
public class PromotionStatisticService {

  @Autowired
  private PromotionStatisticRepository repo;

  public Integer countByPromotionPactName(String promotionPactName) {
    return repo.countByPromotionPactName(promotionPactName);
  }

  public List<PromotionStatistic> getAllPromotionStatistic() {
    return repo.findAll();
  }

  public Optional<PromotionStatistic> getPromotionStatistic(Integer id) {
    return repo.findById(id);
  }

  public List<PromotionStatistic> getPromotionStatisticByPromotionPactName(String promotionPactName) {
    return repo.findByPromotionPactName(promotionPactName);
  }

  public List<PromotionStatistic> getPromotionStatisticByPromoterEmail(String promoterEmail) {
    return repo.findByPromoterEmail(promoterEmail);
  }
}
