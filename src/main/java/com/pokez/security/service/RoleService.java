package com.pokez.security.service;


import com.pokez.security.enums.RoleName;
import com.pokez.security.models.RoleModel;
import com.pokez.security.repository.RoleRepository;
import java.util.Optional;
import javax.management.relation.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {

  @Autowired
  RoleRepository roleRepository;

  public Optional<RoleModel> getByRoleName (RoleName roleName){
    return roleRepository.findByRoleName(roleName);
  }

}
