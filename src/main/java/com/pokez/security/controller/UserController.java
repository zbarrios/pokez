package com.pokez.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.configuration.ResponseHandler;
import com.pokez.security.dto.ChangePassDto;
import com.pokez.security.models.MainUserModel;
import com.pokez.security.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acc-settings")
public class UserController {

  @Autowired
  ResponseHandler responseHandler;
  @Autowired
  UserService userService;
  /**
   *
   * We can use a model of JWT Library. 'UserDetails'
   * Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   * UserDetails user = (UserDetails)principal
   *
   */

  @PostMapping("/change-password")
  public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassDto changePass) throws JsonProcessingException {
    if (!changePass.getNewPassword().equals(changePass.getConfirmPassword()))
      return responseHandler.basicMessageResponse("Passwords don't match.",
          HttpStatus.BAD_REQUEST);
    userService.setNewPasswordInfo(changePass);
    return responseHandler.basicMessageResponse("Successfully password update.",HttpStatus.OK);
  }

}
