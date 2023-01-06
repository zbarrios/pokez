package com.pokez.services;

import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.catchem.PlanningRequestModel;
import com.pokez.repository.PokemonRepository;
import com.pokez.security.models.MainUserModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {

  @Autowired
  PokemonRepository pokemonRepository;

  public List<BattlePokemonModel> getPokemonInfoModel(PlanningRequestModel planning){
    pokemonRepository.getPokemonInfo(planning);

    return null;
  }

}
