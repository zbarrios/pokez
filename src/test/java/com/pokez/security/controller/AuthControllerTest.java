package com.pokez.security.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokez.security.dto.JwtDtoModel;
import com.pokez.security.dto.LoginUserModel;
import com.pokez.security.dto.NewUserModel;
import com.pokez.security.jwt.JwtProvider;
import com.pokez.security.service.UserService;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @MockBean
  UserService userService;
  @MockBean
  JwtProvider jwtProvider;

  @BeforeEach
  void setup() {
  }


  @Test
  void newUserSuccessfullyCase() throws Exception {
    NewUserModel newUser = new NewUserModel("userTest2","test2@mail.com","1234567890","passwordTest");
    MvcResult mvcResult = mockMvc.perform(post("/auth/new-user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String, Object> mapResponse = objectMapper.readValue(response,new TypeReference<>() {});
    Assertions.assertEquals("New registered user.",mapResponse.get("message"));
    Assertions.assertEquals(HttpStatus.CREATED.value(),mvcResult.getResponse().getStatus());
  }

  @Test
  void newUserWhenThisAlreadyExists() throws Exception {
    NewUserModel newUser = new NewUserModel("userTest","test@mail.com","1234567890","passwordTest");
    doThrow(DataIntegrityViolationException.class).when(userService).newUser(any());
    MvcResult mvcResult = mockMvc.perform(post("/auth/new-user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String, Object> mapResponse = objectMapper.readValue(response,new TypeReference<>() {});
    Assertions.assertEquals("The username or email is already registered.",
        mapResponse.get("message"));
    Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),mvcResult.getResponse().getStatus());
  }

  @Test
  void loginSuccessfullyCase() throws Exception {
    LoginUserModel loginData = new LoginUserModel("userTest","passwordTest");
    when(userService.login(any())).thenReturn(new JwtDtoModel("JWT_PASSWORD"));
    MvcResult mvcResult = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginData))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String, Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    JwtDtoModel jwtActual = objectMapper.convertValue(mapResponse.get("data"),JwtDtoModel.class);
    Assertions.assertEquals("JWT_PASSWORD", jwtActual.getToken());
  }

  @Test
  void refreshTokenSuccessfullyCase() throws Exception{
    JwtDtoModel jwtDto = new JwtDtoModel("JWT_PASSWORD");
    when(jwtProvider.refreshToken(any())).thenReturn("JWT_REFRESH_PASSWORD");
    MvcResult mvcResult = mockMvc.perform(post("/auth/refresh")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(jwtDto))).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String, Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    JwtDtoModel actualJwt = objectMapper.convertValue(mapResponse.get("data"),JwtDtoModel.class);
    Assertions.assertEquals("JWT_REFRESH_PASSWORD",actualJwt.getToken());
  }



}
