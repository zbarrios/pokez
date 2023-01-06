package com.pokez.security.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RecoverPasswordDto {

  @NotBlank (message = "Password needs to be filed.")
  @NotNull  (message = "Password needs to be filed.")
  private String password;
  @NotBlank (message = "'Confirm Password' needs to be filed.")
  @NotNull (message = "'Confirm Password' needs to be filed.")
  private String confirmPassword;
  @NotBlank (message = "Token needs to be filed.")
  @NotNull (message = "Token needs to be filed.")
  private String tokenPassword;

  public RecoverPasswordDto() {
  }

  public RecoverPasswordDto(String password, String confirmPassword, String tokenPassword) {
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.tokenPassword = tokenPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getTokenPassword() {
    return tokenPassword;
  }

  public void setTokenPassword(String tokenPassword) {
    this.tokenPassword = tokenPassword;
  }
}
