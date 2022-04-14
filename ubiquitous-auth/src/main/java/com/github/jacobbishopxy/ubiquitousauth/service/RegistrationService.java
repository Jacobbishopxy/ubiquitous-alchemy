/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserRole;
import com.github.jacobbishopxy.ubiquitousauth.dto.FlattenedUserAccount;
import com.github.jacobbishopxy.ubiquitousauth.dto.SimpleUserRole;
import com.github.jacobbishopxy.ubiquitousauth.dto.UserAccountDto;
import com.github.jacobbishopxy.ubiquitousauth.dto.UserPrivilegeDto;
import com.github.jacobbishopxy.ubiquitousauth.dto.UserRoleDto;
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

  public List<UserAccount> getAllUsers(Boolean isActive) {
    if (isActive == null) {
      return userAccountRepo.findAll();
    } else {
      return userAccountRepo.findAllByActiveEquals(isActive);
    }
  }

  public List<FlattenedUserAccount> getAllUsersInFlattenedForm(Boolean isActive) {
    return this
        .getAllUsers(isActive)
        .stream()
        .map(FlattenedUserAccount::fromUserAccount)
        .collect(Collectors.toList());
  }

  public Optional<UserAccount> getUserById(Integer id) {
    return userAccountRepo.findById(id);
  }

  public Optional<UserAccount> getUserByUsername(String username) {
    return userAccountRepo.findByUsername(username);
  }

  public Optional<FlattenedUserAccount> getUserByIdInFlattenedForm(Integer id) {
    return userAccountRepo
        .findById(id)
        .map(FlattenedUserAccount::fromUserAccount);
  }

  public List<UserRole> getAllRoles() {
    return userRoleRepo.findAll();
  }

  public List<SimpleUserRole> getAllRolesInSimpleForm() {
    return userRoleRepo
        .findAll()
        .stream()
        .map(SimpleUserRole::fromUserRole)
        .collect(Collectors.toList());
  }

  public List<UserPrivilege> getAllPrivileges() {
    return userPrivilegeRepo.findAll();
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  public UserPrivilege registerPrivilege(UserPrivilegeDto dto) {
    return userPrivilegeRepo.save(dto.intoUserPrivilege());
  }

  public Optional<UserPrivilege> modifyPrivilege(Integer id, UserPrivilegeDto dto) {
    return userPrivilegeRepo
        .findById(id)
        .map(privilege -> {
          privilege.setName(dto.name());
          privilege.setDescription(dto.description());
          return userPrivilegeRepo.save(privilege);
        });
  }

  public void removePrivilege(Integer id) {
    userPrivilegeRepo.deleteById(id);
  }

  public UserRole registerRole(UserRoleDto dto) {
    // only valid privilege ids are allowed
    List<UserPrivilege> privileges = userPrivilegeRepo.findAllById(dto.userPrivilegeIds());
    return userRoleRepo.save(new UserRole(dto.name(), dto.description(), privileges));
  }

  public Optional<UserRole> modifyRole(Integer id, UserRoleDto dto) {
    // only valid privilege ids are allowed
    List<UserPrivilege> privileges = userPrivilegeRepo.findAllById(dto.userPrivilegeIds());
    return userRoleRepo
        .findById(id)
        .map(role -> {
          role.setName(dto.name());
          role.setDescription(dto.description());
          role.setPrivileges(privileges);
          return userRoleRepo.save(role);
        });
  }

  public void removeRole(Integer id) {
    userRoleRepo.deleteById(id);
  }

  public UserAccount registerUser(UserAccountDto dto) {
    // only valid role ids are allowed
    List<UserRole> roles = userRoleRepo.findAllById(dto.userRoleIds());
    return userAccountRepo.save(new UserAccount(dto.username(), dto.email(), dto.active(), roles));
  }

  public Optional<UserAccount> modifyUser(Integer id, UserAccountDto dto) {
    // only valid role ids are allowed
    List<UserRole> roles = userRoleRepo.findAllById(dto.userRoleIds());
    return userAccountRepo
        .findById(id)
        .map(user -> {
          user.setUsername(dto.username());
          user.setEmail(dto.email());
          user.setActive(dto.active());
          user.setRoles(roles);
          return userAccountRepo.save(user);
        });
  }

  public void removeUser(Integer id) {
    userAccountRepo.deleteById(id);
  }

  public void deactivateUser(Integer id) {
    userAccountRepo
        .findById(id)
        .ifPresent(user -> user.setActive(false));
  }

  public void reactivateUser(Integer id) {
    userAccountRepo
        .findById(id)
        .ifPresent(user -> user.setActive(true));
  }

  public void toggleUserActiveStatus(Integer id) {
    userAccountRepo
        .findById(id)
        .ifPresent(user -> user.setActive(!user.getActive()));
  }

}
