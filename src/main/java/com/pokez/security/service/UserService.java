package com.pokez.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.security.dto.ChangePassDto;
import com.pokez.security.dto.JwtDtoModel;
import com.pokez.security.dto.LoginUserModel;
import com.pokez.security.dto.NewUserModel;
import com.pokez.security.enums.RoleName;
import com.pokez.security.jwt.JwtProvider;
import com.pokez.security.models.MainUserModel;
import com.pokez.security.models.RoleModel;
import com.pokez.security.models.UserModel;
import com.pokez.security.repository.RoleRepository;
import com.pokez.security.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@EnableCaching
public class UserService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  JwtProvider jwtProvider;

  public Optional<UserModel> getUserByUserName(String userName){
    return userRepository.findByUserName(userName);
  }

  public Optional<UserModel> getUserByUserId(Integer id){
    return userRepository.findById(id);
  }

  public Optional<UserModel> getUserByUserNameOrEmail(String userNameOrEmail){
    return userRepository.findByUserNameOrEmail(userNameOrEmail,userNameOrEmail);
  }

  public Optional<UserModel> getByTokenPassword(String tokenPassword){
    return userRepository.findByTokenPassword(tokenPassword);
  }

  public Boolean existsByUserName(String userName){
    return userRepository.existsByUserName(userName);
  }

  /**
   * This function allows you to create a new user for login.
   */
  public void newUser(NewUserModel newUser) throws DataIntegrityViolationException {
    Optional<UserModel> userModel = getUserByUserName(newUser.getUserName());
    if (userModel.isPresent()) throw new DataIntegrityViolationException(newUser.getUserName());
    UserModel user = new UserModel(
        newUser.getUserName(),
        newUser.getEmail(),
        newUser.getMobile(),
        passwordEncoder.encode(newUser.getPassword()));
    Set<RoleModel> roleModels = new HashSet<>();
    roleModels.add(roleRepository.findByRoleName(RoleName.ROLE_USER).get());
    if (newUser.getRoles().contains("admin")) {
      roleModels.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN).get());
    }
    user.setRoles(roleModels);
    userRepository.save(user);
  }

  /**
   * This function logs a user.
   */

  public JwtDtoModel login(LoginUserModel loginUser) throws JsonProcessingException {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateToken(authentication);
    return new JwtDtoModel(jwt);
  }

  public void save(UserModel user){userRepository.save(user);
  }

  public void changePassword(UserModel user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    user.setTokenPassword(null);
    save(user);
  }

  public MainUserModel getUserInfoFromSecurityContext(){
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (MainUserModel) principal;
  }

  public void setNewPasswordInfo(ChangePassDto changePass){
    MainUserModel main = getUserInfoFromSecurityContext();
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(main.getUsername(), changePass.getPassword()));
    Optional<UserModel> userOpt = getUserByUserName(main.getUsername());
    changePassword(userOpt.get(), changePass.getNewPassword());
  }

}
