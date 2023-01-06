package com.pokez.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.configuration.RandomHandler;
import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.battle.TrainerPokemonModel;
import com.pokez.models.catchem.CatchPokemonModel;
import com.pokez.models.catchem.PlanningRequestModel;
import com.pokez.models.pokemon.CurrentMovesModel;
import com.pokez.repository.CatchRepository;
import com.pokez.repository.PokemonRepository;
import com.pokez.security.models.MainUserModel;
import com.pokez.security.models.UserModel;
import com.pokez.security.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatchService {

  @Autowired
  CatchRepository catchRepository;
  @Autowired
  PokemonService pokemonService;
  @Autowired
  UserService userService;
  @Autowired
  PokemonRepository pokemonRepository;
  @Autowired
  RandomHandler randomHandler;
  @Autowired
  MovesByPokeService movesByPokeService;

  private static final Integer Team_Size = 3;



  public List savePokemonPlanning(PlanningRequestModel planningRequest){
    MainUserModel mainUser = userService.getUserInfoFromSecurityContext();
    return catchRepository.savePokemonPlanning(mainUser,planningRequest);
  }

  public Object savePokemonCatch(PlanningRequestModel planningRequest)
      throws JsonProcessingException {
    UserModel user = userService.getUserByUserName(userService.
        getUserInfoFromSecurityContext().getUsername()).get();
    List<CatchPokemonModel> pokemonList = pokemonRepository.getPokemonInfo(planningRequest);
    HashMap<String,CurrentMovesModel> movesByPoke = movesByPokeService.getListOfMovesByPokemon(pokemonList);
   // movesByPoke.values().forEach(System.out::println);
    List <CatchPokemonModel> finalList = setStatsOfPokemon(pokemonList,movesByPoke);
    catchRepository.savePokemonCatch(user,finalList);
    return finalList;
  }

  public List<CatchPokemonModel> setStatsOfPokemon(List<CatchPokemonModel> pokemonList,
      HashMap<String,CurrentMovesModel> movesByPoke){
    List<CatchPokemonModel> finalList = new ArrayList<>();
    for (CatchPokemonModel basicPokemon:pokemonList) {
      CurrentMovesModel currentPokeMoves = movesByPoke.get(basicPokemon.getPokemonName());
      CatchPokemonModel catchPokemon = new CatchPokemonModel(
          basicPokemon.getPokeId(),
          basicPokemon.getPokemonName(),
          randomHandler.calculateStat(basicPokemon.getAttack()),
          randomHandler.calculateStat(basicPokemon.getDefense()),
          randomHandler.calculateSpeed(basicPokemon),
          randomHandler.calculateStat(basicPokemon.getStamina()),
          basicPokemon.getFirstType(),
          basicPokemon.getSecondType(),
          randomHandler.randomMove(currentPokeMoves.getFastMoves()),
          randomHandler.randomMove(currentPokeMoves.getChargedMoves())
      );
      finalList.add(catchPokemon);
    }
    return finalList;
  }

  public TrainerPokemonModel getTrainerTeamByIds(List<Integer> pokeList){
    UserModel user = userService.getUserByUserName(userService.
        getUserInfoFromSecurityContext().getUsername()).get();
    return new TrainerPokemonModel(user.getId(), user.getUserName(),
        catchRepository.retrieveTeamPokemonByIds(user,pokeList));
  }

  public TrainerPokemonModel getTrainerTeamByUser(String trainer){
    UserModel user = userService.getUserByUserName(trainer).get();
    return new TrainerPokemonModel(user.getId(), user.getUserName(),
        catchRepository.retrieveTeamPokemonByUserName(user, Team_Size));
  }





}
