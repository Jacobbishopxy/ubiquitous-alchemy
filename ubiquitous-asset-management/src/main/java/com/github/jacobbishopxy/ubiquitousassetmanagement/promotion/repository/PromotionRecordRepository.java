/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repository;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.domain.PromotionRecord;

import org.springframework.data.jpa.repository.*;

public interface PromotionRecordRepository
    extends JpaRepository<PromotionRecord, Integer>, JpaSpecificationExecutor<PromotionRecord> {

  // delete all records where score is zero
  // @Modifying
  // @Query("delete promotion_record p where p.score = 0")
  // int deleteZeroScores();

  List<PromotionRecord> findByPromotionPactName(String promotionPactName);

  List<PromotionRecord> findByPromoterEmail(String promoterEmail);

  List<PromotionRecord> findByPromotionPactNameAndPromoterEmail(
      String promotionPactName, String promoterEmail);

  long countByPromotionPactName(String promotionPactName);
}
