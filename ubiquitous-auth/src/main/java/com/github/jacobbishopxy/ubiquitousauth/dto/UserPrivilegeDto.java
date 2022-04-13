/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.dto;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;

public record UserPrivilegeDto(
    String name,
    String description) {

  public UserPrivilege intoUserPrivilege() {
    return new UserPrivilege(name, description);
  }

}
