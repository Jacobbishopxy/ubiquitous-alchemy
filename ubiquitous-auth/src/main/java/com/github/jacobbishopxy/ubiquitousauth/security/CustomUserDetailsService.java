/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserAccount;
import com.github.jacobbishopxy.ubiquitousauth.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private RegistrationService registrationService;

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<UserAccount> userAccountOpt = registrationService
        .getUserByUsername(username);

    if (userAccountOpt.isEmpty()) {
      return new CustomUserDetails("visitor", true, List.of(new SimpleGrantedAuthority("ROLE_VISITOR")));
    }

    UserAccount userAccount = userAccountOpt.get();

    List<GrantedAuthority> authorities = userAccount
        .getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        .collect(Collectors.toList());

    List<GrantedAuthority> privileges = userAccount
        .getPrivileges()
        .stream()
        .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
        .collect(Collectors.toList());

    authorities.addAll(privileges);

    return new CustomUserDetails(username, userAccount.getActive(), authorities);
  }
}
