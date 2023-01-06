package com.pokez.models.catchem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatchPokemonModel {

  @JsonProperty("pokemon_catch_id")
  private Integer pokemonCatchId;
  @JsonProperty("poke_id")
  private Integer pokeId;
  @JsonProperty("pokemon_name")
  private String pokemonName;
  private Integer attack;
  private Integer defense;
  private Integer speed;
  private Integer stamina;
  @JsonProperty("first_type")
  private Integer firstType;
  @JsonProperty("second_type")
  private Integer secondType;
  @JsonProperty("fast_move")
  private String fastMove;
  @JsonProperty("charged_move")
  private String chargedMove;

  public CatchPokemonModel(Integer pokeId, String pokemonName,
      Integer attack,
      Integer defense, Integer speed, Integer stamina, Integer firstType, Integer secondType,
      String fastMove, String chargedMove) {
    this.pokeId = pokeId;
    this.pokemonName = pokemonName;
    this.attack = attack;
    this.defense = defense;
    this.speed = speed;
    this.stamina = stamina;
    this.firstType = firstType;
    this.secondType = secondType;
    this.fastMove = fastMove;
    this.chargedMove = chargedMove;
  }

  public CatchPokemonModel(Integer pokeId, String pokemonName, Integer attack, Integer defense,
      Integer stamina, Integer firstType, Integer secondType) {
    this.pokeId = pokeId;
    this.pokemonName = pokemonName;
    this.attack = attack;
    this.defense = defense;
    this.stamina = stamina;
    this.firstType = firstType;
    this.secondType = secondType;
  }

  public CatchPokemonModel() {
  }

  public Integer getPokemonCatchId() {
    return pokemonCatchId;
  }

  public void setPokemonCatchId(Integer pokemonCatchId) {
    this.pokemonCatchId = pokemonCatchId;
  }

  public Integer getPokeId() {
    return pokeId;
  }

  public void setPokeId(Integer pokeId) {
    this.pokeId = pokeId;
  }

  public String getPokemonName() {
    return pokemonName;
  }

  public void setPokemonName(String pokemonName) {
    this.pokemonName = pokemonName;
  }

  public Integer getAttack() {
    return attack;
  }

  public void setAttack(Integer attack) {
    this.attack = attack;
  }

  public Integer getDefense() {
    return defense;
  }

  public void setDefense(Integer defense) {
    this.defense = defense;
  }

  public Integer getSpeed() {
    return speed;
  }

  public void setSpeed(Integer speed) {
    this.speed = speed;
  }

  public Integer getStamina() {
    return stamina;
  }

  public void setStamina(Integer stamina) {
    this.stamina = stamina;
  }

  public Integer getFirstType() {
    return firstType;
  }

  public void setFirstType(Integer firstType) {
    this.firstType = firstType;
  }

  public Integer getSecondType() {
    return secondType;
  }

  public void setSecondType(Integer secondType) {
    this.secondType = secondType;
  }

  public String getFastMove() {
    return fastMove;
  }

  public void setFastMove(String fastMove) {
    this.fastMove = fastMove;
  }

  public String getChargedMove() {
    return chargedMove;
  }

  public void setChargedMove(String chargedMove) {
    this.chargedMove = chargedMove;
  }
}
