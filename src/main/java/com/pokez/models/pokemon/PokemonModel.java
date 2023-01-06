package com.pokez.models.pokemon;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for Pokemon info.
 */
public class PokemonModel {

  @JsonProperty("pokemon_name")
  @JsonAlias({"pokemon_name","name"})
  private String pokemonName;
  @JsonProperty("poke_id")
  @JsonAlias({"pokemon_id","id"})
  private Integer pokeId;

  public PokemonModel(String pokemonName, Integer pokeId) {
    this.pokemonName = pokemonName;
    this.pokeId = pokeId;
  }

  public PokemonModel() {
  }

  public String getPokemonName() {
    return pokemonName;
  }

  public void setPokemonName(String pokemonName) {
    this.pokemonName = pokemonName;
  }

  public Integer getPokeId() {
    return pokeId;
  }

  public void setPokeId(Integer pokeId) {
    this.pokeId = pokeId;
  }
}
