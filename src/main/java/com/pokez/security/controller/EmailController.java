package com.pokez.security.controller;

import com.pokez.configuration.ResponseHandler;
import com.pokez.security.dto.RecoverPasswordDto;
import com.pokez.security.dto.EmailValuesDto;
import com.pokez.security.models.UserModel;
import com.pokez.security.service.EmailService;
import com.pokez.security.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

  @Autowired
  EmailService emailService;
  @Autowired
  UserService userService;
  @Autowired
  ResponseHandler responseHandler;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Value("${spring.mail.username}")
  private String mailFrom;

  private static final String SUBJECT = "Reset Password";

  @PostMapping("/send-email")
  public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDto emailDto) {
    Optional<UserModel> userOpt = userService.getUserByUserNameOrEmail(emailDto.getMailTo());
    if (userOpt.isEmpty())
      return responseHandler.basicMessageResponse(
          "Don't exist any user associate to this credentials.", HttpStatus.NOT_FOUND);
    emailService.sendEmail(emailService.setMailAndToken(emailDto,userOpt.get()));
    return responseHandler.basicMessageResponse("We already send to you a email.", HttpStatus.OK);
  }

  /**
   * We can manage the field errors with the Response Handler or with A Binding Result parameter
   * public ResponseEntity<?> changePassword(@Valid @RequestBody RecoverPasswordDto recoverPassDto,
   * BindingResults bindingResults)
   * if(bindingResult.hasErrors()) {
   *       Map<String, String> errorMap = new HashMap<>();
   *       bindingResult.getFieldErrors().forEach((error) -> {
   *         errorMap.put(error.getField(),
   *             error.getRejectedValue() + "Bad Field " + error.getDefaultMessage());
   *             });
   *  return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
   * }
   */

  @PostMapping("/recover-password")
  public ResponseEntity<?> recoverPassword(@Valid @RequestBody RecoverPasswordDto recoverPassDto) {
    if(!recoverPassDto.getPassword().equals(recoverPassDto.getConfirmPassword()))
      return responseHandler.basicMessageResponse("Passwords don't match.",
          HttpStatus.BAD_REQUEST);
    Optional<UserModel> userOpt = userService.getByTokenPassword(recoverPassDto.getTokenPassword());
    if(userOpt.isEmpty())
      return responseHandler.basicMessageResponse(
          "Don't exist any user associate to this credentials.", HttpStatus.NOT_FOUND);
    userService.changePassword(userOpt.get(),recoverPassDto.getPassword());
    return responseHandler.basicMessageResponse(
        "Password update successfully", HttpStatus.OK);
  }

}