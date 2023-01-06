package com.pokez.models.battle;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TrainerPokemonModel {

  @JsonProperty("trainer_id")
  private Integer trainerId;
  @JsonProperty("trainer_name")
  private String trainerName;
  @JsonProperty("pokemon_list")
  private List<BattlePokemonModel> pokemonList;

  public TrainerPokemonModel(Integer trainerId, String trainerName,
      List<BattlePokemonModel> pokemonList) {
    this.trainerId = trainerId;
    this.trainerName = trainerName;
    this.pokemonList = pokemonList;
  }

  public TrainerPokemonModel(TrainerPokemonModel trainer) {
    this.trainerId = trainer.getTrainerId();
    this.trainerName = trainer.getTrainerName();
    this.pokemonList = trainer.getPokemonList();
  }

  @Override
  public String toString() {
    return "TrainerPokemonModel{" +
        "trainerId=" + trainerId +
        ", trainerName='" + trainerName + '\'' +
        ", pokemonList=" + pokemonList +
        '}';
  }

  public TrainerPokemonModel() {
  }

  public Integer getTrainerId() {
    return trainerId;
  }

  public void setTrainerId(Integer trainerId) {
    this.trainerId = trainerId;
  }

  public String getTrainerName() {
    return trainerName;
  }

  public void setTrainerName(String trainerName) {
    this.trainerName = trainerName;
  }

  public List<BattlePokemonModel> getPokemonList() {
    return pokemonList;
  }

  public void setPokemonList(List<BattlePokemonModel> pokemonList) {
    this.pokemonList = pokemonList;
  }
}
