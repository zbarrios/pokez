package com.pokez.security.models;

import com.pokez.security.enums.RoleName;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role", schema = "public")
public class RoleModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @NotNull
  @Enumerated(EnumType.STRING)
  private RoleName roleName;

  public RoleModel() {
  }

  public RoleModel(RoleName roleName) {
    this.roleName = roleName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public RoleName getRolName() {
    return roleName;
  }

  public void setRolName(RoleName roleName) {
    this.roleName = roleName;
  }
}
