/**
 * Created by Jacob Xie on 3/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AccumulatedPerformance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccumulatedPerformanceRepository extends JpaRepository<AccumulatedPerformance, Long> {

  Optional<AccumulatedPerformance> findByPactId(Long pactId);

  List<AccumulatedPerformance> findByPactIdIn(List<Long> pactId);

}
