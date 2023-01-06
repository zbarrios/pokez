package com.pokez.models.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TournamentTrainerModel{

  @JsonProperty("trainer_id")
  private Integer trainerId;
  @JsonProperty("pokemon_trainer")
  private BattlePokemonModel pokemonTrainer;

  public TournamentTrainerModel() {
  }

  public TournamentTrainerModel(Integer trainerId, BattlePokemonModel pokemonTrainer) {
    this.trainerId = trainerId;
    this.pokemonTrainer = pokemonTrainer;
  }

  public Integer getTrainerId() {
    return trainerId;
  }

  public void setTrainerId(Integer trainerId) {
    this.trainerId = trainerId;
  }

  public BattlePokemonModel getPokemonTrainer() {
    return pokemonTrainer;
  }

  public void setPokemonTrainer(BattlePokemonModel pokemonTrainer) {
    this.pokemonTrainer = pokemonTrainer;
  }
}
