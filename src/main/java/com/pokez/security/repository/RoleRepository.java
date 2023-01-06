package com.pokez.security.repository;

import com.pokez.security.enums.RoleName;
import com.pokez.security.models.RoleModel;
import java.util.Optional;
import javax.management.relation.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel,Integer> {
  Optional<RoleModel> findByRoleName(RoleName roleName);

}
