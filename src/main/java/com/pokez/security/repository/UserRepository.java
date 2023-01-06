package com.pokez.security.repository;

import com.pokez.security.models.UserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {

  Optional<UserModel> findByUserName(String userName);

  Optional<UserModel> findByUserNameOrEmail(String userName, String email);

  Optional<UserModel> findByTokenPassword (String tokenPassword);

  boolean existsByUserName(String userName);

  boolean existsByEmail(String email);

}
