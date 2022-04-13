/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;

public record FlattenedUserAccount(
    String username,
    String email,
    boolean active,
    Collection<SimpleUserRole> roles,
    Collection<SimpleUserPrivilege> privileges) {

  public static FlattenedUserAccount fromUserAccount(UserAccount userAccount) {

    Collection<SimpleUserRole> roles = userAccount
        .getRoles()
        .stream()
        .map(SimpleUserRole::fromUserRole)
        .collect(Collectors.toList());

    Collection<SimpleUserPrivilege> privileges = userAccount
        .getPrivileges()
        .stream()
        .map(SimpleUserPrivilege::fromUserPrivilege)
        .collect(Collectors.toList());

    return new FlattenedUserAccount(
        userAccount.getUsername(),
        userAccount.getEmail(),
        userAccount.getActive(),
        roles,
        privileges);
  }

}
