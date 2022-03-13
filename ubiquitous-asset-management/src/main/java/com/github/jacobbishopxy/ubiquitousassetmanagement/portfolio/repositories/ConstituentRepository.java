/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConstituentRepository
    extends JpaRepository<Constituent, Integer>, JpaSpecificationExecutor<Constituent> {

  List<Constituent> findByAdjustmentRecordId(int adjustmentRecordId);

  void deleteByAdjustmentRecordId(int adjustmentRecordId);

  List<Constituent> findByAdjustmentRecordIdIn(List<Integer> adjustmentRecordIds);

  void deleteByAdjustmentRecordIdIn(List<Integer> adjustmentRecordIds);
}
