package com.pokez.models.battle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pokez.models.pokemon.moves.FastMoveModel;
import com.pokez.models.pokemon.moves.ChargedMoveModel;

public class BattlePokemonModel {

  @JsonProperty("pokemon_catch_id")
  private Integer pokemonCatchId;
  @JsonProperty("poke_id")
  private Integer pokeId;
  @JsonProperty("pokemon_name")
  private String pokemonName;
  private Integer attack;
  private Integer defense;
  private Integer speed;
  private Double stamina;
  @JsonProperty("first_type")
  private String firstType;
  @JsonProperty("second_type")
  private String secondType;
  @JsonProperty("fast_move")
  private FastMoveModel fastMove;
  @JsonProperty("charged_move")
  private ChargedMoveModel chargedMove;
  private Integer chosenMove;

  public BattlePokemonModel(Integer pokemonCatchId, Integer pokeId, String pokemonName,
      Integer attack, Integer defense, Integer speed, Double stamina, String firstType,
      String secondType, FastMoveModel fastMove, ChargedMoveModel chargedMove) {
    this.pokemonCatchId = pokemonCatchId;
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

  public BattlePokemonModel(BattlePokemonModel bPokemon) {
    this.pokemonCatchId = bPokemon.getPokemonCatchId();
    this.pokeId = bPokemon.getPokeId();
    this.pokemonName = bPokemon.getPokemonName();
    this.attack = bPokemon.getAttack();
    this.defense = bPokemon.getDefense();
    this.speed = bPokemon.getSpeed();
    this.stamina = bPokemon.getStamina();
    this.firstType = bPokemon.getFirstType();
    this.secondType = bPokemon.getSecondType();
    this.fastMove = new FastMoveModel(bPokemon.getFastMove());
    this.chargedMove = new ChargedMoveModel(bPokemon.getChargedMove());
  }

  @Override
  public String toString() {
    return "BattlePokemonModel{" +
        "pokemonCatchId=" + pokemonCatchId +
        ", pokeId=" + pokeId +
        ", pokemonName='" + pokemonName + '\'' +
        ", attack=" + attack +
        ", defense=" + defense +
        ", speed=" + speed +
        ", stamina=" + stamina +
        ", firstType='" + firstType + '\'' +
        ", secondType='" + secondType + '\'' +
        ", fastMove=" + fastMove +
        ", chargedMove=" + chargedMove +
        ", chosenMove=" + chosenMove +
        '}';
  }
  public BattlePokemonModel() {
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

  public Double getStamina() {
    return stamina;
  }

  public void setStamina(Double stamina) {
    this.stamina = stamina;
  }

  public String getFirstType() {
    return firstType;
  }

  public void setFirstType(String firstType) {
    this.firstType = firstType;
  }

  public String getSecondType() {
    return secondType;
  }

  public void setSecondType(String secondType) {
    this.secondType = secondType;
  }

  public FastMoveModel getFastMove() {
    return fastMove;
  }

  public void setFastMove(FastMoveModel fastMove) {
    this.fastMove = fastMove;
  }

  public ChargedMoveModel getChargedMove() {
    return chargedMove;
  }

  public void setChargedMove(ChargedMoveModel chargedMove) {
    this.chargedMove = chargedMove;
  }

  public Integer getChosenMove() {
    return chosenMove;
  }

  public void setChosenMove(Integer chosenMove) {
    this.chosenMove = chosenMove;
  }
}
