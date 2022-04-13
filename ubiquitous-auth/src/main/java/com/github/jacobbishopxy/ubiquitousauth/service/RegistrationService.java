/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;
import com.github.jacobbishopxy.ubiquitousauth.dto.FlattenedUserAccount;
import com.github.jacobbishopxy.ubiquitousauth.repository.UserAccountRepo;
import com.github.jacobbishopxy.ubiquitousauth.repository.UserPrivilegeRepo;
import com.github.jacobbishopxy.ubiquitousauth.repository.UserRoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

  @Autowired
  UserPrivilegeRepo userPrivilegeRepo;

  @Autowired
  UserRoleRepo userRoleRepo;

  @Autowired
  UserAccountRepo userAccountRepo;

  // =======================================================================
  // Query methods
  // =======================================================================

  public List<UserAccount> getAllUsers() {
    return userAccountRepo.findAll();
  }

  public List<FlattenedUserAccount> getAllUsersInFlattenedForm() {
    return userAccountRepo
        .findAll()
        .stream()
        .map(FlattenedUserAccount::fromUserAccount)
        .collect(Collectors.toList());
  }

  public Optional<UserAccount> getUserById(Integer id) {
    return userAccountRepo.findById(id);
  }

  public Optional<FlattenedUserAccount> getUserByIdInFlattenedForm(Integer id) {
    return userAccountRepo
        .findById(id)
        .map(FlattenedUserAccount::fromUserAccount);
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  // TODO:
}
