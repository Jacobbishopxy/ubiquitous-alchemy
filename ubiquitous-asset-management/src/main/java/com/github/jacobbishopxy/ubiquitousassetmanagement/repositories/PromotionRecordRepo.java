/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.repositories;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

import org.springframework.data.jpa.repository.*;

public interface PromotionRecordRepo
    extends JpaRepository<PromotionRecord, Integer>, JpaSpecificationExecutor<PromotionRecord> {

  // delete all records where score is zero
  @Modifying
  @Query("delete promotion_record p where p.score = 0")
  int deleteZeroScores();

}
