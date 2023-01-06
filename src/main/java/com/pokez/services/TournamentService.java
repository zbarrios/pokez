package com.pokez.services;

import com.pokez.configuration.GlobalVariable;
import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.battle.TournamentTrainerModel;
import com.pokez.models.battle.TrainerPokemonModel;
import com.pokez.repository.CatchRepository;
import com.pokez.security.models.UserModel;
import com.pokez.security.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import net.bytebuddy.pool.TypePool.Empty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

  @Autowired
  CatchRepository catchRepository;
  @Autowired
  UserService userService;
  @Autowired
  BattleService battleService;

  public List<TrainerPokemonModel> setListForTournament(){
    List<TrainerPokemonModel> trainerList = getListOfTrainer();
    Collections.shuffle(trainerList);
    double log = Math.log(trainerList.size()) / Math.log(2);
    double tournamentSize = Math.pow(2,Math.ceil(log));
    int nBye = (int) (tournamentSize>trainerList.size()?tournamentSize-trainerList.size():0);
    Stack<TrainerPokemonModel> classifiedList = new Stack<>();
    while (nBye<trainerList.size()){
      classifiedList.push(battleService.teamBattle(trainerList.remove(0),trainerList.remove(0)));
    }
    classifiedList.addAll(trainerList);
    return getTournamentWinner(classifiedList);
  }

  public Stack<TrainerPokemonModel> getTournamentWinner(Stack<TrainerPokemonModel> trainerList){
    if (trainerList.size()==1)
      return trainerList;
    Stack<TrainerPokemonModel> classifiedList = new Stack<>();
    while (!trainerList.empty()){
      classifiedList.push(battleService.teamBattle(trainerList.pop(),trainerList.pop()));
    }
    return getTournamentWinner(classifiedList);
  }

  public List<TrainerPokemonModel> getListOfTrainer() {
    List<TournamentTrainerModel> pokemonByTrainer = catchRepository.retrieveTeamsForTournament();
    List<TrainerPokemonModel> trainerList = new ArrayList<>();
    List<BattlePokemonModel> pokemonList = new ArrayList<>();
    AtomicInteger actual = new AtomicInteger(), witness = new AtomicInteger();
    for (TournamentTrainerModel pokemon : pokemonByTrainer) {
      if (actual.get() != pokemon.getTrainerId()) {
        if (actual.get() != witness.get())
          addTrainerToList(actual.get(), pokemonList, trainerList, witness);
        pokemonList.clear();
        actual.set(pokemon.getTrainerId());
      }
      if (pokemonList.size()==GlobalVariable.TEAM_SIZE)
        continue;
      pokemonList.add(pokemon.getPokemonTrainer());
      if (pokemonList.size() == GlobalVariable.TEAM_SIZE)
        addTrainerToList(actual.get(), pokemonList, trainerList,witness);
    }
    if (actual.get() != witness.get())
      addTrainerToList(actual.get(), pokemonList, trainerList,witness);
    return trainerList;
  }

  /**
   *commi
   * @param id
   * @param pokemonList
   * @param trainerList
   * @param wit
   */

  public void addTrainerToList(Integer id, List<BattlePokemonModel> pokemonList,
      List<TrainerPokemonModel> trainerList, AtomicInteger wit){
    UserModel user = userService.getUserByUserId(id).get();
    TrainerPokemonModel trainer = new TrainerPokemonModel(user.getId(), user.getUserName(),
        new ArrayList<>(pokemonList));
    trainerList.add(trainer);
    wit.set(id);
  }

}
