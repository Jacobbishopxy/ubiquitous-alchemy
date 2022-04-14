/**
 * Created by Jacob Xie on 4/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.controller;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousauth.Constants;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;
import com.github.jacobbishopxy.ubiquitousauth.domain.UserRole;
import com.github.jacobbishopxy.ubiquitousauth.dto.SimpleUserRole;
import com.github.jacobbishopxy.ubiquitousauth.dto.UserAccountDto;
import com.github.jacobbishopxy.ubiquitousauth.dto.UserPrivilegeDto;
import com.github.jacobbishopxy.ubiquitousauth.dto.UserRoleDto;
import com.github.jacobbishopxy.ubiquitousauth.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_REGISTRATION)
public class RegistrationController {

  @Autowired
  private RegistrationService registrationService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/users")
  public List<UserAccount> showAllUsers(@RequestParam(required = false) Boolean isActive) {
    return registrationService.getAllUsers(isActive);
  }

  @GetMapping("/user/{id}")
  public UserAccount showUserById(@PathVariable Integer id) {
    return registrationService
        .getUserById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("User with id %d not found!", id)));
  }

  @GetMapping("/roles_privileges")
  public List<UserRole> showAllRolesWithPrivileges() {
    return registrationService.getAllRoles();
  }

  @GetMapping("/roles")
  public List<SimpleUserRole> showAllRoles() {
    return registrationService.getAllRolesInSimpleForm();
  }

  @GetMapping("/privileges")
  public List<UserPrivilege> showAllPrivileges() {
    return registrationService.getAllPrivileges();
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/privilege")
  public UserPrivilege registerPrivilege(@RequestBody UserPrivilegeDto dto) {
    return registrationService.registerPrivilege(dto);
  }

  @PutMapping("/privilege/{id}")
  public UserPrivilege modifyPrivilege(@PathVariable Integer id, @RequestBody UserPrivilegeDto dto) {
    return registrationService
        .modifyPrivilege(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Privilege with id %d not found!", id)));
  }

  @DeleteMapping("/privilege/{id}")
  public void deletePrivilege(@PathVariable Integer id) {
    registrationService.removePrivilege(id);
  }

  @PostMapping("/role")
  UserRole registerRole(@RequestBody UserRoleDto dto) {
    return registrationService.registerRole(dto);
  }

  @PutMapping("/role/{id}")
  UserRole modifyRole(@PathVariable Integer id, @RequestBody UserRoleDto dto) {
    return registrationService
        .modifyRole(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Role with id %d not found!", id)));
  }

  @DeleteMapping("/role/{id}")
  void deleteRole(@PathVariable Integer id) {
    registrationService.removeRole(id);
  }

  @PostMapping("/user")
  UserAccount registerUser(@RequestBody UserAccountDto dto) {
    return registrationService.registerUser(dto);
  }

  @PutMapping("/user/{id}")
  UserAccount modifyUser(@PathVariable Integer id, @RequestBody UserAccountDto dto) {
    return registrationService
        .modifyUser(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("User with id %d not found!", id)));
  }

  @DeleteMapping("/user/{id}")
  void deleteUser(@PathVariable Integer id) {
    registrationService.removeUser(id);
  }

  @PatchMapping("/user/{id}/toggle_active")
  void toggleUserActive(@PathVariable Integer id) {
    registrationService.toggleUserActiveStatus(id);
  }
}
