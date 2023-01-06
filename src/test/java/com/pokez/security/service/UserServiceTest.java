package com.pokez.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.exceptions.AccAlreadyExistsException;
import com.pokez.models.pokemon.PokemonModel;
import com.pokez.security.dto.ChangePassDto;
import com.pokez.security.dto.LoginUserModel;
import com.pokez.security.dto.NewUserModel;
import com.pokez.security.enums.RoleName;
import com.pokez.security.models.RoleModel;
import com.pokez.security.models.UserModel;
import com.pokez.security.repository.UserRepository;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class UserServiceTest {

  @Autowired
  UserService userService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setup() {
    jdbcTemplate.execute("Truncate Table user_role ");
    userRepository.deleteAll();
    RoleModel adminRole = new RoleModel(RoleName.ROLE_ADMIN);
    adminRole.setId(1);
    RoleModel userRole = new RoleModel(RoleName.ROLE_USER);
    userRole.setId(2);
    Set<RoleModel> roleModels = new HashSet<>(Arrays.asList(adminRole,userRole));
    UserModel user = new UserModel("userTest", "test@mail.com", "1234567890",
        passwordEncoder.encode("passwordTest"));
    user.setRoles(roleModels);
    user.setTokenPassword("token");
    userRepository.save(user);
  }

  @Test
  void getUserByUserNameSuccessfullyCase(){
    UserModel actualUser = userService.getUserByUserName("userTest").get();
    Assertions.assertEquals("userTest",actualUser.getUserName());
    Assertions.assertEquals("test@mail.com",actualUser.getEmail());
  }

  @Test
  void getUserByUserNameOrEmailSuccessfullyCase(){
    UserModel actualUser = userService.getUserByUserNameOrEmail("test@mail.com").get();
    Assertions.assertEquals("userTest",actualUser.getUserName());
    Assertions.assertEquals("test@mail.com",actualUser.getEmail());
  }

  @Test
  void getByTokenPasswordSuccessfullyCase(){
    UserModel actualUser = userService.getByTokenPassword("token").get();
    Assertions.assertEquals("userTest",actualUser.getUserName());
    Assertions.assertEquals("test@mail.com",actualUser.getEmail());
  }

  @Test
  void existsByUserName(){
    Assertions.assertTrue(userService.existsByUserName("userTest"));
  }

  @Test
  void newUserSuccessfullyCase(){
    NewUserModel newUser = new NewUserModel("userTest2","test2@mail.com","1234567890","passwordTest");
    userService.newUser(newUser);
    UserModel actualUser = userRepository.findByUserName(newUser.getUserName()).get();
    Assertions.assertEquals(newUser.getUserName(),actualUser.getUserName());
    Assertions.assertEquals(newUser.getEmail(),actualUser.getEmail());
    Assertions.assertEquals(newUser.getMobile(),actualUser.getMobile());
    Assertions.assertEquals(1,actualUser.getRoles().size());
  }

  @Test
  void newUserWithAdminRoleSuccessfullyCase(){
    NewUserModel newUser = new NewUserModel("userTest2","test2@mail.com","1234567890","passwordTest");
    newUser.setRoles(new ArrayList<>(List.of("admin")));
    userService.newUser(newUser);
    UserModel actualUser = userRepository.findByUserName(newUser.getUserName()).get();
    Assertions.assertEquals(newUser.getUserName(),actualUser.getUserName());
    Assertions.assertEquals(newUser.getEmail(),actualUser.getEmail());
    Assertions.assertEquals(newUser.getMobile(),actualUser.getMobile());
    Assertions.assertEquals(2,actualUser.getRoles().size());
  }

  @Test
  void loginSuccessfullyCase() throws Exception {
    LoginUserModel login = new LoginUserModel("userTest","passwordTest");
    Assertions.assertFalse(userService.login(login).getToken().isEmpty());
  }

  @Test
  void loginNotAuthenticationCase() {
    LoginUserModel login = new LoginUserModel("userTest","passwordFalseTest");
    Exception exception = assertThrows(BadCredentialsException.class,
        () -> userService.login(login));
    Assertions.assertNotNull(exception);
    Assertions.assertEquals("Bad credentials",exception.getMessage());
  }

  @Test
  void saveSuccessfullyCase() {
    UserModel testUser = new UserModel("userTest2", "test2@mail.com", "1234567890",
        passwordEncoder.encode("passwordTest"));
    userService.save(testUser);
    UserModel actualUser = userRepository.findByUserName(testUser.getUserName()).get();
    Assertions.assertEquals(testUser.getUserName(),actualUser.getUserName());
    Assertions.assertEquals(testUser.getEmail(),actualUser.getEmail());
    Assertions.assertEquals(testUser.getMobile(),actualUser.getMobile());
    Assertions.assertEquals(testUser.getRoles(),actualUser.getRoles());
    Assertions.assertEquals(testUser.getPassword(),actualUser.getPassword());
  }

  @Test
  void changePasswordSuccessfullyCase() {
    UserModel testUser = new UserModel("userTest2", "test2@mail.com", "1234567890",
        passwordEncoder.encode("passwordTest"));
    String newPassword = "newPasswordTest";
    userService.changePassword(testUser,newPassword);
    UserModel actualUser = userRepository.findByUserName(testUser.getUserName()).get();
    Assertions.assertNotNull(actualUser.getPassword());
    Assertions.assertFalse(actualUser.getPassword().isEmpty());
    Assertions.assertNull(actualUser.getTokenPassword());
  }


}
