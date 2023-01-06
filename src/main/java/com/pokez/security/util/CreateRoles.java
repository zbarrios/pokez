package com.pokez.security.util;

import com.pokez.security.service.RoleService;
import javax.management.relation.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS SE DEBERÁ ELIMINAR O BIEN COMENTAR EL CÓDIGO
 *
 */

@Component
public class CreateRoles implements CommandLineRunner {

  @Autowired
  RoleService roleService;

  @Override
  public void run(String... args) throws Exception {
    /** Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
     Rol rolUser = new Rol(RolNombre.ROLE_USER);
     roleService.save(rolAdmin);
     roleService.save(rolUser);
     **/
  }
}
