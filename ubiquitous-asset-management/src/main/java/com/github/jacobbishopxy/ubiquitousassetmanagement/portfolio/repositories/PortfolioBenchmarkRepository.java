/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioBenchmark;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioBenchmarkRepository extends JpaRepository<PortfolioBenchmark, Integer> {

}
