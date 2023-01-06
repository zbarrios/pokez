package com.pokez.models.pokemon;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for Pokemon Base Stats.
 */
@JsonIgnoreProperties("form")
public class StatsModel extends PokemonModel {

  @JsonProperty("base_attack")
  @JsonAlias("base_attack")
  private int baseAttack;
  @JsonProperty("base_defense")
  @JsonAlias("base_defense")
  private int baseDefense;
  @JsonProperty("base_stamina")
  @JsonAlias("base_stamina")
  private int baseStamina;

  public StatsModel() {
  }

  /**
   * Constructor for Pokemon Stats Model.
   */
  public StatsModel(String name, Integer id, int baseAttack, int baseDefense, int baseStamina) {
    super(name, id);
    this.baseAttack = baseAttack;
    this.baseDefense = baseDefense;
    this.baseStamina = baseStamina;
  }

  public int getBaseAttack() {
    return baseAttack;
  }

  public void setBaseAttack(int baseAttack) {
    this.baseAttack = baseAttack;
  }

  public int getBaseDefense() {
    return baseDefense;
  }

  public void setBaseDefense(int baseDefense) {
    this.baseDefense = baseDefense;
  }

  public int getBaseStamina() {
    return baseStamina;
  }

  public void setBaseStamina(int baseStamina) {
    this.baseStamina = baseStamina;
  }
}
