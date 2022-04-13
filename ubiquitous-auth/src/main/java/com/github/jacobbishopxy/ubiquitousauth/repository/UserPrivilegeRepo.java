/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.repository;

import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivilegeRepo extends JpaRepository<UserPrivilege, Integer> {

  Optional<UserPrivilege> findByName(String name);

  void deleteByName(String name);

}
