/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivilegeRepo extends JpaRepository<UserPrivilegeRepo, Integer> {

  Optional<UserPrivilegeRepo> findByName(String name);

  void deleteByName(String name);

}
