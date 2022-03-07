/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionPact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repositories.PromotionPactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PromotionPactService
 *
 * PromotionPactService is used to manage promotion pacts.
 */
@Service
public class PromotionPactService {

  @Autowired
  private PromotionPactRepository repo;

  public List<PromotionPact> getAllPromotionPacts() {
    return repo.findAll();
  }

  public Optional<PromotionPact> getPromotionPact(String name) {
    return repo.findByName(name);
  }

  public PromotionPact createPromotionPact(PromotionPact promotionPact) {
    return repo.save(promotionPact);
  }

  public Optional<PromotionPact> updatePromotionPact(String name, PromotionPact promotionPact) {
    return repo.findByName(name).map(
        record -> {
          record.setDateRange(promotionPact.getDateRange());
          record.setDescription(promotionPact.getDescription());
          return repo.save(record);
        });
  }

  public void deletePromotionPact(String name) {
    repo.deleteByName(name);
  }

}
