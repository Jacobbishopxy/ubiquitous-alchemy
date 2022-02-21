/**
 * Created by Jacob Xie on 2/17/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.repositories;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Promoter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoterRepository extends JpaRepository<Promoter, String> {

  public Promoter findByNickname(String nickname);

}
