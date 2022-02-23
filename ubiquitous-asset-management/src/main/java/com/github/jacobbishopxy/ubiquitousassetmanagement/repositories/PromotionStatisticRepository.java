/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionStatistic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionStatisticRepository extends JpaRepository<PromotionStatistic, Integer> {

  Integer countByPromotionPactName(String promotionPactName);

  Optional<List<PromotionStatistic>> findByPromotionPactName(String promotionPactName);

  Optional<List<PromotionStatistic>> findByPromoterEmail(String promoterEmail);
}
