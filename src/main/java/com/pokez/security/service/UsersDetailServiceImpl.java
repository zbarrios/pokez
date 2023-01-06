package com.pokez.security.service;

import com.pokez.security.models.MainUserModel;
import com.pokez.security.models.UserModel;
import com.pokez.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
    UserModel user = userRepository.findByUserName(userNameOrEmail).get();
    return MainUserModel.build(user);
  }
}
