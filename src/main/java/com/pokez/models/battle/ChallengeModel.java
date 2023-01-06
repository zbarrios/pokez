package com.pokez.models.battle;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UniqueElements;

public class ChallengeModel {

  @NotNull
  @NotEmpty
  @UniqueElements(message = "Your team cant contain duplicate pokemon.")
  @JsonProperty("pokemon_team")
  private List
      <@NotBlank(message = "Pokemon name needs to be filed.")
      @Pattern(regexp = "^\\d+$", message = "Pokemon name cant contain numbers or spaces.")
          String> pokemonTeam;
  @NotNull
  @NotBlank (message = "User name needs to be filed.")
  private String trainerTwo;


  public ChallengeModel(@NotNull
      List<String> pokemonTeam,
      String trainerTwo) {
    this.pokemonTeam = pokemonTeam;
    this.trainerTwo = trainerTwo;
  }

  public ChallengeModel() {
  }

  public List<String> getPokemonTeam() {
    return pokemonTeam;
  }

  public void setPokemonTeam(List<String> pokemonTeam) {
    this.pokemonTeam = pokemonTeam;
  }

  public String getTrainerTwo() {
    return trainerTwo;
  }

  public void setTrainerTwo(String trainerTwo) {
    this.trainerTwo = trainerTwo;
  }
}
