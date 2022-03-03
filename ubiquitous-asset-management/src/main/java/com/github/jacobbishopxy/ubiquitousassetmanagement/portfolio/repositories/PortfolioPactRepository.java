/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioPact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioPactRepository extends JpaRepository<PortfolioPact, Integer> {

  public Optional<PortfolioPact> findByAlias(String alias);

  public void deleteByAlias(String alias);

}
