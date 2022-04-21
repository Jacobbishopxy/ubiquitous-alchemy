/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.config;

import com.github.jacobbishopxy.ubiquitousauth.security.CustomCasService;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@PropertySource("classpath:cas.properties")
public class CasConfig {

  @Value("${casKey}")
  private String casKey;

  @Value("${casTimeout}")
  private Integer casTimeout;

  @Value("${casUrl}")
  private String casUrl;

  @Value("${casLoginUrl}")
  private String casLoginUrl;

  @Value("${casLogoutUrl}")
  private String casLogoutUrl;

  @Value("${baseUrl}")
  private String baseUrl;

  @Value("${baseLoginPath}")
  private String baseLoginPath;

  @Value("${baseLogoutPath}")
  private String baseLogoutPath;

  public String getCasKey() {
    return casKey;
  }

  public Integer getCasTimeout() {
    return casTimeout;
  }

  public String getCasUrl() {
    return casUrl;
  }

  public String getCasLoginUrl() {
    return casLoginUrl;
  }

  public String getCasLogoutUrl() {
    return casLogoutUrl;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public String getBaseLoginPath() {
    return baseLoginPath;
  }

  public String getBaseLogoutPath() {
    return baseLogoutPath;
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
    entryPoint.setLoginUrl(casLoginUrl);
    entryPoint.setServiceProperties(this.serviceProperties());
    return entryPoint;
  }

  @Bean
  protected AuthenticationManager authenticationManager() {
    return new ProviderManager(this.casAuthenticationProvider());
  }

  @Bean
  public CasAuthenticationFilter casAuthenticationFilter(
      AuthenticationManager authenticationManager,
      ServiceProperties serviceProperties) throws Exception {
    CasAuthenticationFilter filter = new CasAuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager);
    filter.setServiceProperties(serviceProperties);
    return filter;
  }

  @Bean
  public ServiceProperties serviceProperties() {
    ServiceProperties serviceProperties = new ServiceProperties();
    serviceProperties.setService(baseUrl + baseLoginPath);
    serviceProperties.setSendRenew(false);
    return serviceProperties;
  }

  @Bean
  public TicketValidator ticketValidator() {
    return new Cas20ServiceTicketValidator(casUrl);
  }

  @Bean
  public CustomCasService customCasService() {
    return new CustomCasService();
  }

  @Bean
  public CasAuthenticationProvider casAuthenticationProvider() {
    CasAuthenticationProvider provider = new CasAuthenticationProvider();
    provider.setServiceProperties(this.serviceProperties());
    provider.setTicketValidator(this.ticketValidator());
    provider.setAuthenticationUserDetailsService(customCasService());
    provider.setKey(casKey);
    return provider;
  }

  @Bean
  SecurityContextLogoutHandler securityContextLogoutHandler() {
    return new SecurityContextLogoutHandler();
  }

  @Bean
  LogoutFilter logoutFilter() {
    LogoutFilter filter = new LogoutFilter(casLogoutUrl, securityContextLogoutHandler());
    filter.setFilterProcessesUrl(baseLogoutPath);
    return filter;
  }

  @Bean
  public SingleSignOutFilter singleSignOutFilter() {
    SingleSignOutFilter filter = new SingleSignOutFilter();
    filter.setIgnoreInitConfiguration(true);
    return filter;
  }
}
