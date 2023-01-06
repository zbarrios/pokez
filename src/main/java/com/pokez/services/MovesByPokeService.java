package com.pokez.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.catchem.CatchPokemonModel;
import com.pokez.models.catchem.PlanningRequestModel;
import com.pokez.models.pokemon.CurrentMovesModel;
import com.pokez.repository.mongo.MovesByPokeRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MovesByPokeService {

  @Autowired
  MovesByPokeRepo movesByPokeRepo;
  @Autowired
  MongoTemplate mongoTemplate;

  public void insertListOfMovesByPokemon(List<CurrentMovesModel> movesByPoke) throws JsonProcessingException {
    movesByPokeRepo.deleteAll();
    movesByPokeRepo.saveAll(movesByPoke);
  }

  public HashMap<String, CurrentMovesModel> getListOfMovesByPokemon(List<CatchPokemonModel> pokemonList) throws JsonProcessingException {
    Query query = new Query();
    List<String> pokemonNames = new ArrayList<>();
    pokemonList.forEach(poke->pokemonNames.add(poke.getPokemonName()));
    query.addCriteria(Criteria.where("pokemonName").in(pokemonNames));
    List<CurrentMovesModel> currentMoves = mongoTemplate.find(query,CurrentMovesModel.class);
    return dropByForm(currentMoves,"Normal");
  }

  public HashMap<String, CurrentMovesModel> dropByForm(List<CurrentMovesModel> pokeList, String form){
    HashMap<String, CurrentMovesModel> revision = new HashMap<>();
    pokeList.forEach(poke -> {
      if (!revision.containsKey(poke.getPokemonName())){
        revision.put(poke.getPokemonName(),poke);
      }
      if (poke.getForm().equals(form)){
        revision.replace(poke.getPokemonName(),poke);
      }
    });
    return revision;
  }

}
