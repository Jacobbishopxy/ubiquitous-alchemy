/**
 * Created by Jacob Xie on 2/17/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.repositories;

import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoterRepository extends JpaRepository<Promoter, String> {

  public Optional<Promoter> findByNickname(String nickname);

}
