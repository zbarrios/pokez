package com.pokez.models.catchem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UniqueElements;

/**
 * Model for save the info of planning Pokemon.
 */
public class PlanningRequestModel {

  @NotNull
  @UniqueElements(message = "Your caught planning cant contain duplicate pokemon.")
  @JsonProperty("pokemon_list")
  private List
      <@NotBlank(message = "Pokemon name needs to be filed.")
      @Pattern(regexp = "^[a-zA-Z]*$", message = "Pokemon name cant contain numbers or spaces.")
      String> pokemonList;

  public PlanningRequestModel() {
  }

  public PlanningRequestModel(List<@NotBlank(message = "Pokemon name needs to be filed.")
      @Pattern(regexp = "^[a-zA-Z]*$", message = "Pokemon name cant contain numbers or spaces.")
          String> pokemonList) {
    this.pokemonList = pokemonList;
  }

  public List<String> getPokemonList() {
    return pokemonList;
  }

  public void setPokemonList(List<String> pokemonList) {
    this.pokemonList = pokemonList;
  }
}