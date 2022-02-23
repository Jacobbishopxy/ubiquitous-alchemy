/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionStatistic;
import com.github.jacobbishopxy.ubiquitousassetmanagement.repositories.PromotionStatisticRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Optional<List<PromotionStatistic>> getPromotionStatisticByPromotionPactName(String promotionPactName) {
    return repo.findByPromotionPactName(promotionPactName);
  }

}
