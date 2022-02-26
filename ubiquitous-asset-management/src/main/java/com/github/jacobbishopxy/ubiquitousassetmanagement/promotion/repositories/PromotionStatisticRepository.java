/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionStatistic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionStatisticRepository extends JpaRepository<PromotionStatistic, Integer> {

  Integer countByPromotionPactName(String promotionPactName);

  List<PromotionStatistic> findByPromotionPactName(String promotionPactName);

  List<PromotionStatistic> findByPromoterEmail(String promoterEmail);

  Optional<PromotionStatistic> findByPromotionPactNameAndPromoterEmail(String promotionPactName, String promoterEmail);
}
