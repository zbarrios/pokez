package com.pokez.security.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NewUserModel {

  @NotBlank (message = "User name needs to be filed.")
  private String userName;
  @Email (message = "Please verify email entered.")
  private String email;
  @NotBlank(message = "Mobile needs to be filed.")
  @Pattern(regexp = "^\\d{10}$", message = "Please verify mobile number.")
  private String mobile;
  @NotBlank (message = "Password name needs to be filed.")
  private String password;
  private List<String> roles = new ArrayList<>();

  /**
   * Constructor for the security new user.
   */
  public NewUserModel(String userName, String email, String mobile, String password) {
    this.userName = userName;
    this.email = email;
    this.mobile = mobile;
    this.password = password;
  }

  public NewUserModel() {
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
