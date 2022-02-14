/**
 * Created by Jacob Xie on 2/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.repositories;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

import org.springframework.data.repository.CrudRepository;

public interface PromotionRecordRepo extends CrudRepository<PromotionRecord, Integer> {

}
