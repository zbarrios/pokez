package com.pokez.security.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokez.security.dto.ChangePassDto;
import com.pokez.security.service.UserService;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @MockBean
  UserService userService;

  @Test
  @WithMockUser(username = "adminUser", roles = {"ADMIN", "USER"})
  void changePasswordSuccessfullyCase() throws Exception {
    ChangePassDto changePass = new ChangePassDto(
        "oldPass","newPass","newPass");
    MvcResult mvcResult = mockMvc.perform(post("/acc-settings/change-password")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(changePass)))
        .andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Successfully password update.",mapResponse.get("message"));
  }

  @Test
  @WithMockUser(username = "adminUser", roles = {"ADMIN", "USER"})
  void changePasswordWhenNotMatchCase() throws Exception {
    ChangePassDto changePass = new ChangePassDto(
        "oldPass","newPass","passNew");
    MvcResult mvcResult = mockMvc.perform(post("/acc-settings/change-password")
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(changePass)))
        .andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Passwords don't match.",mapResponse.get("message"));
  }


}
