/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.dto;

import java.util.Collection;

public record UserAccountDto(
    String username,
    String email,
    boolean active,
    Collection<Integer> userRoleIds) {

}
