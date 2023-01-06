package com.pokez.security.service;

import com.pokez.security.enums.RoleName;
import com.pokez.security.models.RoleModel;
import com.pokez.security.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class RoleServiceTest {

  @Autowired
  RoleService roleService;
  @Autowired
  RoleRepository roleRepository;

  @Test
  void getByRoleNameSuccessFullyCase(){
    RoleModel roleAdmin = roleService.getByRoleName(RoleName.ROLE_ADMIN).get();
    RoleModel roleUser = roleService.getByRoleName(RoleName.ROLE_USER ).get();
    Assertions.assertEquals(RoleName.ROLE_ADMIN,roleAdmin.getRolName());
    Assertions.assertEquals(RoleName.ROLE_USER, roleUser.getRolName());
  }

}
