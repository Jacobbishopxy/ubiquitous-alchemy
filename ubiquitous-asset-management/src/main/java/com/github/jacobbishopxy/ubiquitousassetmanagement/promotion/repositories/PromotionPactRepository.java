/**
 * Created by Jacob Xie on 2/23/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.repositories;

import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionPact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionPactRepository extends JpaRepository<PromotionPact, String> {

  public Optional<PromotionPact> findByName(String name);

  public void deleteByName(String name);
}
