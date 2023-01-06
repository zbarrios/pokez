package com.pokez.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePassDto {

  @NotBlank(message = "Password needs to be filed.")
  @NotNull(message = "Password needs to be filed.")
  private String password;
  @NotBlank (message = "New Password needs to be filed.")
  @NotNull (message = "New Password needs to be filed.")
  private String newPassword;
  @NotBlank (message = "'Confirm New Password' needs to be filed.")
  @NotNull (message = "'Confirm New Password' needs to be filed.")
  private String confirmPassword;

  public ChangePassDto(String password, String newPassword, String confirmPassword) {
    this.password = password;
    this.newPassword = newPassword;
    this.confirmPassword = confirmPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
