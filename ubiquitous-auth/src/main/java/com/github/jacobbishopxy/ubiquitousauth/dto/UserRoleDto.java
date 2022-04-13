package com.github.jacobbishopxy.ubiquitousauth.dto;

import java.util.Collection;

public record UserRoleDto(
    String name,
    String description,
    Collection<Integer> userPrivilegeIds) {

}
