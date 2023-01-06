package com.pokez.security.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MainUserModel implements UserDetails {

  private String userName;
  private String email;
  private String mobile;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public MainUserModel(String userName, String email, String mobile, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.userName = userName;
    this.email = email;
    this.mobile = mobile;
    this.password = password;
    this.authorities = authorities;
  }

  public MainUserModel() {
  }

  public static MainUserModel build(UserModel user){
    List<GrantedAuthority> authorities =
        user.getRoles().stream().map(roleModel -> new SimpleGrantedAuthority(
            roleModel.getRolName().name())).collect(Collectors.toList());
    return new MainUserModel(user.getUserName(), user.getEmail(), user.getMobile(),
        user.getPassword(), authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getEmail() {
    return email;
  }

  public String getMobile() {
    return mobile;
  }
}
