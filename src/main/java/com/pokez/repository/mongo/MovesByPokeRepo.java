package com.pokez.repository.mongo;

import com.pokez.models.pokemon.CurrentMovesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovesByPokeRepo extends MongoRepository<CurrentMovesModel,String> {

}
