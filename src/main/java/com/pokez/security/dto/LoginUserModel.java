package com.pokez.security.dto;

import javax.validation.constraints.NotBlank;

public class LoginUserModel {

  @NotBlank
  private String userName;
  @NotBlank
  private String password;

  public LoginUserModel(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
