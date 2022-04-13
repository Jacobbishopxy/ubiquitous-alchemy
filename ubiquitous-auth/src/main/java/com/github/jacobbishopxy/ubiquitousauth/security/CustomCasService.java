/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.security;

// import java.util.Map;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomCasService extends AbstractCasAssertionUserDetailsService {

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @Override
  protected UserDetails loadUserDetails(Assertion assertion) {
    String username = assertion.getPrincipal().getName();

    // Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
    // for (String key : attributes.keySet()) {
    // System.out.println(">>>>>>>>>> " + key + ": " + attributes.get(key));
    // }

    return customUserDetailsService.loadUserByUsername(username);
  }
}
