/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.repository;

import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

  Optional<UserRole> findByName(String name);

  void deleteByName(String name);

}
