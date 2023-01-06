package com.pokez.services;

import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.battle.DamageModel;
import com.pokez.models.pokemon.EffectivenessModel;
import com.pokez.repository.TypeRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

  @Autowired
  TypeRepository typeRepository;

  public static HashMap<String,HashMap<String,Double>> typeEffectiveness;

  public void updateEffectiveness(){
    typeEffectiveness = typeRepository.getTypeEffectivenessList();
    //typeEffectiveness.entrySet().forEach(System.out::println);
  }

  public Double calculateEffectiveness(DamageModel damageAttack, BattlePokemonModel pokemonDefender) {
    HashMap<String,Double> types = typeEffectiveness.get(damageAttack.getTypeMove());
    Double secondType = pokemonDefender.getSecondType()==null?1:types.get(pokemonDefender.getSecondType());
    return types.get(pokemonDefender.getFirstType()) * secondType;
  }


}
