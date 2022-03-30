/**
 * Created by Jacob Xie on 3/3/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Pact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PactRepository extends JpaRepository<Pact, Long> {

  Optional<Pact> findByAlias(String alias);

  void deleteByAlias(String alias);

  List<Pact> findByIsActive(Boolean isActive);

}
