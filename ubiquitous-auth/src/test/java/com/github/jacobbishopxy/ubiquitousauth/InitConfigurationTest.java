/**
 * Created by Jacob Xie on 4/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jacobbishopxy.ubiquitousauth.config.InitDataConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = InitDataConfig.class)
public class InitConfigurationTest {

  @Autowired
  private InitDataConfig initDataConfig;

  @Test
  public void testInitDataConfig() {
    assertThat(initDataConfig.getShouldInitialize()).isTrue();
    assertThat(initDataConfig.getInitPrivileges().size()).isEqualTo(3);
    assertThat(initDataConfig.getInitRoles().size()).isEqualTo(3);
  }

}
