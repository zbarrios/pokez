package com.pokez.security.service;

import com.pokez.security.dto.EmailValuesDto;
import com.pokez.security.models.UserModel;
import com.pokez.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class EmailServiceTest {

  @Autowired
  EmailService emailService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @BeforeEach
  void setup(){
    userRepository.deleteAll();
  }

  @Test
  void setMailAndTokenSuccessfullyCase(){
    UserModel testUser = new UserModel("userTest", "test@mail.com", "1234567890",
        passwordEncoder.encode("passwordTest"));
    EmailValuesDto actualMailDto = emailService.setMailAndToken(new EmailValuesDto(),testUser);
    Assertions.assertFalse(actualMailDto.getMailFrom().isEmpty());
    Assertions.assertFalse(actualMailDto.getTokenPassword().isEmpty());
    Assertions.assertEquals("Recover Password",actualMailDto.getSubject());
    Assertions.assertEquals(testUser.getEmail(),actualMailDto.getMailTo());
    Assertions.assertEquals(testUser.getUserName(),actualMailDto.getUserName());
  }



}
