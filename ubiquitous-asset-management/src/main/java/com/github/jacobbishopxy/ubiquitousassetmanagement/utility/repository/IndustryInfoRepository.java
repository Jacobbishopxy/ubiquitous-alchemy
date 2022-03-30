/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.repository;

import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.domain.IndustryInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryInfoRepository extends JpaRepository<IndustryInfo, Integer> {

  public Optional<IndustryInfo> findByName(String name);

}
