/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.dto;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserRole;

public record SimpleUserRole(
    String name,
    String description) {

  public static SimpleUserRole fromUserRole(UserRole userRole) {
    return new SimpleUserRole(userRole.getName(), userRole.getDescription());
  }

}
