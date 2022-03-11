/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BenchmarkRepository
    extends JpaRepository<Benchmark, Integer>, JpaSpecificationExecutor<Benchmark> {

  List<Benchmark> findByAdjustmentRecordId(int adjustmentRecordId);

  List<Benchmark> findByAdjustmentRecordIdIn(List<Integer> adjustmentRecordIds);
}
