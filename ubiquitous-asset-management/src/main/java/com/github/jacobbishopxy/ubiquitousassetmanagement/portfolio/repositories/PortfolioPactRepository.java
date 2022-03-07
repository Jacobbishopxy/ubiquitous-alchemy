/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioPact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioPactRepository extends JpaRepository<PortfolioPact, Integer> {

  Optional<PortfolioPact> findByAlias(String alias);

  void deleteByAlias(String alias);

  List<PortfolioPact> findByIsActive(Boolean isActive);

}
