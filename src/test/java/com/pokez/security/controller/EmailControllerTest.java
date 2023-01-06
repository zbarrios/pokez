package com.pokez.security.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokez.security.dto.EmailValuesDto;
import com.pokez.security.dto.RecoverPasswordDto;
import com.pokez.security.models.UserModel;
import com.pokez.security.repository.UserRepository;
import com.pokez.security.service.EmailService;
import com.pokez.security.service.UserService;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class EmailControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  UserRepository userRepository;
  @MockBean
  UserService userService;
  @MockBean
  EmailService emailService;


  @Test
  void sendEmailSuccessfullyCase() throws Exception {
    UserModel user = new UserModel("userTest", "test@mail.com", "1234567890",
        passwordEncoder.encode("passwordTest"));
    when(userService.getUserByUserNameOrEmail(any())).thenReturn(Optional.of(user));
    MvcResult mvcResult = mockMvc.perform(post("/email-password/send-email")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new EmailValuesDto()))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("We already send to you a email.",mapResponse.get("message"));
  }

  @Test
  void sendEmailWhenUserNotExistCase() throws Exception {
    when(userService.getUserByUserNameOrEmail(any())).thenReturn(Optional.empty());
    MvcResult mvcResult = mockMvc.perform(post("/email-password/send-email")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new EmailValuesDto()))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Don't exist any user associate to this credentials.",
        mapResponse.get("message"));
  }

  @Test
  void recoverPasswordSuccessfullyCase() throws Exception {
    UserModel user = new UserModel("userTest", "test@mail.com", "1234567890",
        passwordEncoder.encode("passwordTest"));
    RecoverPasswordDto recover = new RecoverPasswordDto(
        "newPassword","newPassword","token");
    when(userService.getByTokenPassword(any())).thenReturn(Optional.of(user));
    MvcResult mvcResult = mockMvc.perform(post("/email-password/recover-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recover))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Password update successfully",
        mapResponse.get("message"));
  }

  @Test
  void recoverWhenPasswordNotMatchCase() throws Exception {
    RecoverPasswordDto recover = new RecoverPasswordDto(
        "newPassword","passwordNew","token");
    MvcResult mvcResult = mockMvc.perform(post("/email-password/recover-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recover))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Passwords don't match.", mapResponse.get("message"));
  }

  @Test
  void recoverPasswordWhenUserNotExistsCase() throws Exception {
    when(userService.getByTokenPassword(any())).thenReturn(Optional.empty());
    RecoverPasswordDto recover = new RecoverPasswordDto(
        "newPassword","newPassword","token");
    MvcResult mvcResult = mockMvc.perform(post("/email-password/recover-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recover))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Don't exist any user associate to this credentials.",
        mapResponse.get("message"));
  }





}
