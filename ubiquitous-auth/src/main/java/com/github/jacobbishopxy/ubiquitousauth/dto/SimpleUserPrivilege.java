/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.dto;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;

public record SimpleUserPrivilege(
    String name,
    String description) {

  public static SimpleUserPrivilege fromUserPrivilege(UserPrivilege userPrivilege) {
    return new SimpleUserPrivilege(userPrivilege.getName(), userPrivilege.getDescription());
  }

}
