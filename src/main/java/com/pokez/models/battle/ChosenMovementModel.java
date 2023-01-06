package com.pokez.models.battle;

import com.pokez.models.pokemon.moves.ChargedMoveModel;
import com.pokez.models.pokemon.moves.FastMoveModel;

public class ChosenMovementModel extends BattlePokemonModel{

  private Integer choice;

  public ChosenMovementModel(Integer pokemonCatchId, Integer pokeId, String pokemonName,
      Integer attack, Integer defense, Integer speed, Double stamina, String firstType,
      String secondType, FastMoveModel fastMove,
      ChargedMoveModel chargedMove, Integer choice) {
    super(pokemonCatchId, pokeId, pokemonName, attack, defense, speed, stamina, firstType,
        secondType,
        fastMove, chargedMove);
    this.choice = choice;
  }

  public ChosenMovementModel(Integer choice) {
    this.choice = choice;
  }

  public ChosenMovementModel() {
  }

  public Integer getChoice() {
    return choice;
  }

  public void setChoice(Integer choice) {
    this.choice = choice;
  }
}
