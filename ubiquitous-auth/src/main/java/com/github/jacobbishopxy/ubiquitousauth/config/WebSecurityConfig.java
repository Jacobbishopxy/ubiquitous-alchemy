/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.config;

import com.github.jacobbishopxy.ubiquitousauth.Constants;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CasConfig casConfig;

  @Autowired
  private SingleSignOutFilter singleSignOutFilter;

  @Autowired
  private LogoutFilter logoutFilter;

  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private CasAuthenticationFilter casAuthenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/is_logged_in").permitAll()
        .antMatchers(casConfig.getBaseLoginPath(), casConfig.getBaseLogoutPath()).permitAll()
        .antMatchers(Constants.API_VERSION + Constants.API_INFORMATION).permitAll()
        .antMatchers(Constants.API_VERSION + Constants.API_REGISTRATION).hasRole("admin")
        .anyRequest().authenticated()
        .and()
        .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .addFilter(casAuthenticationFilter)
        .addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class)
        .addFilterBefore(logoutFilter, LogoutFilter.class);
  }
}
