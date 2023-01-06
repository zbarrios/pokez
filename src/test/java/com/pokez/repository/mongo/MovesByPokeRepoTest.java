package com.pokez.repository.mongo;

import com.pokez.models.pokemon.CurrentMovesModel;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
@AutoConfigureTestDatabase
class MovesByPokeRepoTest {


  @Autowired
  MovesByPokeRepo movesByPokeRepo;
  @Autowired
  MongoTemplate mongoTemplate;

  @BeforeEach
  void cleanUpDatabase() {
    this.movesByPokeRepo.deleteAll();
  }

  @Test
  public void saveMovesByPokeSuccessfullyCase() {
    CurrentMovesModel currentMoves = new CurrentMovesModel(
        "Bulbasaur",
        1,
        new ArrayList<>(Arrays.asList( "Vine Whip", "Tackle")),
        new ArrayList<>(Arrays.asList( "Sludge Bomb", "Seed Bomb")),
        "Normal",
        "1");
    movesByPokeRepo.save(currentMoves);
    // Query query = new Query(Criteria.where("serialId").is("1"));
    // query.addCriteria(Criteria.where("serialId").is("1"));
    CurrentMovesModel actualCurrentMoves = mongoTemplate.findById("1",CurrentMovesModel.class);
    Assertions.assertEquals(currentMoves.getFastMoves(),actualCurrentMoves.getFastMoves());
    Assertions.assertEquals(currentMoves.getChargedMoves(),actualCurrentMoves.getChargedMoves());
    Assertions.assertEquals(currentMoves.getPokemonName(),actualCurrentMoves.getPokemonName());
    Assertions.assertEquals(currentMoves.getForm(),actualCurrentMoves.getForm());

  }

}

