package com.pokez.security.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.configuration.ResponseHandler;
import com.pokez.security.dto.JwtDtoModel;
import com.pokez.security.dto.LoginUserModel;
import com.pokez.security.dto.NewUserModel;
import com.pokez.security.jwt.JwtProvider;
import com.pokez.security.service.RoleService;
import com.pokez.security.service.UserService;
import java.text.ParseException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Security class controller.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

  @Autowired
  UserService userService;
  @Autowired
  ResponseHandler responseHandler;
  @Autowired
  JwtProvider jwtProvider;


  /**
   * Endpoint to register a new user in the application.
   */
  @PostMapping("/new-user")
  public ResponseEntity<Object> newUser(@Valid @RequestBody NewUserModel newUser) {
    try {
      userService.newUser(newUser);
      return responseHandler.basicMessageResponse("New registered user.", HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return responseHandler.basicMessageResponse("Password is required.", HttpStatus.BAD_REQUEST);
    } catch (DataIntegrityViolationException e) {
      return responseHandler.basicMessageResponse("The username or email is already registered.",
          HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Endpoint to log in according to a user in the application.
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(
      @Valid @RequestBody LoginUserModel loginUser) throws JsonProcessingException {
    return responseHandler.basicObjectResponse(userService.login(loginUser), HttpStatus.OK);
  }

  @PostMapping("/refresh")
  public ResponseEntity<Object> refresh(@RequestBody JwtDtoModel jwtDto)
      throws ParseException {
    //String token = jwtProvider.refreshToken(jwtDto);
    return responseHandler.basicObjectResponse(new JwtDtoModel(
        jwtProvider.refreshToken(jwtDto)), HttpStatus.OK);
  }
}
