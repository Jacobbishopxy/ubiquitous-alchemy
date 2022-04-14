/**
 * Created by Jacob Xie on 4/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.jacobbishopxy.ubiquitousauth.domain.UserPrivilege;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(InitConfigurationTest.class)
@SpringBootTest
public class DevTest {

  @Test
  void toSet() {

    UserPrivilege p1 = new UserPrivilege();
    p1.setId(1);
    p1.setName("p1");

    UserPrivilege p2 = new UserPrivilege();
    p2.setId(2);
    p2.setName("p2");

    UserPrivilege p3 = new UserPrivilege();
    p3.setId(2);
    p3.setName("p3");

    UserPrivilege p4 = new UserPrivilege();
    p4.setId(4);
    p4.setName("p4");

    Set<UserPrivilege> privileges = new HashSet<>(List.of(p1, p2, p3, p4));

    for (UserPrivilege privilege : privileges) {
      System.out.println(">>>>>>>>>>>>> " + privilege.getId() + " " + privilege.getName());
    }

    assertEquals(privileges.size(), 3);
  }

}
