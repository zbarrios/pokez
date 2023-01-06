package com.pokez.configuration;

import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.catchem.CatchPokemonModel;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomHandler {

  Random random = new Random();

  public Double randomDouble() {
    return random.nextDouble();
  }

  public Integer randomInteger(Integer integer) {
    return random.nextInt(integer);
  }

  public Integer calculateStat(Integer baseStat){
    return baseStat + baseStat * randomInteger(GlobalVariable.POKEMON_IV+1)/100;
  }

  public Integer calculateSpeed(CatchPokemonModel pokemon){
    return calculateStat(pokemon.getAttack()+pokemon.getDefense()+
        pokemon.getStamina().intValue())/3;
  }

  public String randomMove(List<String> moves) {
    return moves.get(randomInteger(moves.size()));
  }

}
