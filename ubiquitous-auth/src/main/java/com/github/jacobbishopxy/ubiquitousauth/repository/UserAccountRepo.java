/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.repository;

import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepo extends JpaRepository<UserAccount, Integer> {

  Optional<UserAccount> findByEmail(String email);

  Optional<UserAccount> findByUsername(String username);

  void deleteByEmail(String email);

  void deleteByUsername(String username);

}
