package com.pokez.models;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class AccountModel {

  @NotBlank(message = "Name needs to be filed.")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "Account name cant contain numbers or spaces.")
  private String name;
  @NotBlank(message = "Email needs to be filed.")
  @Email(message = "Please verify ema"
      + "l entered.")
  private String email;
  @NotBlank(message = "Mobile needs to be filed.")
  @Pattern(regexp = "^\\d{10}$", message = "Please verify mobile number.")
  private String mobile;

  public AccountModel(String name, String email, String mobile) {
    this.name = name;
    this.email = email;
    this.mobile = mobile;
  }

  public AccountModel() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
}
