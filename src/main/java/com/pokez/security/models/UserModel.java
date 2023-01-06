package com.pokez.security.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_acc", schema = "public")
public class UserModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @NotNull
  @Column(unique = true)
  private String userName;
  @NotNull
  @Column(unique = true)
  private String email;
  @NotNull
  private String mobile;
  @NotNull
  private String password;
  private String tokenPassword;
  @NotNull
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleModel> roles = new HashSet<>();

  public UserModel(@NotNull String userName, @NotNull String email, @NotNull String mobile,
      @NotNull String password) {
    this.userName = userName;
    this.email = email;
    this.mobile = mobile;
    this.password = password;
  }

  public UserModel() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  @NotNull
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @NotNull
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @NotNull
  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @NotNull
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @NotNull
  public Set<RoleModel> getRoles() {
    return roles;
  }

  public void setRoles(
      @NotNull Set<RoleModel> securityRoleModels) {
    this.roles = securityRoleModels;
  }

  public String getTokenPassword() {
    return tokenPassword;
  }

  public void setTokenPassword(String tokenPassword) {
    this.tokenPassword = tokenPassword;
  }
}
