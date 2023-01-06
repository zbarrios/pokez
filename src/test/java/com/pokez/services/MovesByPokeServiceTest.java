package com.pokez.services;

import static org.mockito.Mockito.when;

import com.pokez.models.pokemon.CurrentMovesModel;
import com.pokez.repository.mongo.MovesByPokeRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class MovesByPokeServiceTest {

  @MockBean
  PogoRequestService pogoRequestService;
  @Autowired
  MovesByPokeService movesByPokeService;
  @Autowired
  MovesByPokeRepo movesByPokeRepo;

  @BeforeEach
  void cleanUpDatabase() {
    this.movesByPokeRepo.deleteAll();
  }


}