package com.pokez.security.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class JwtDtoModel {

  private String token;

  /**
   * Constructor for the security Jwt Dto.
   */
  public JwtDtoModel(String token) {
    this.token = token;
  }

  public JwtDtoModel() {
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }



  /*

  private String token;private String bearer = "Bearer";
  private String userName;
  private Collection<? extends GrantedAuthority> authorities;


  public JwtDtoModel(String token, String userName,
      Collection<? extends GrantedAuthority> authorities) {
    this.token = token;
    this.userName = userName;
    this.authorities = authorities;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getBearer() {
    return bearer;
  }

  public void setBearer(String bearer) {
    this.bearer = bearer;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }
*/
}
