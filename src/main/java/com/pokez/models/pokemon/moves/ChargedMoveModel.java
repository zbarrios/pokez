package com.pokez.models.pokemon.moves;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for get the Charged Moves from Pogo Api Response.
 */
@JsonIgnoreProperties({"duration", "energy_delta", "heal_scalar"})
public class ChargedMoveModel extends FastMoveModel {

  @JsonProperty("critical_chance")
  private Double criticalChance;

  public ChargedMoveModel(Integer moveId, String name, Double power, Double staminaLossScaler,
      String type, Double staminaStatus, Double criticalChance) {
    super(moveId, name, power, staminaLossScaler, type, staminaStatus);
    this.criticalChance = criticalChance;
  }

  public ChargedMoveModel(ChargedMoveModel chargedMove) {
    super(chargedMove.getMoveId(), chargedMove.getMoveName(), chargedMove.getPower(),
        chargedMove.getStaminaLossScale(), chargedMove.getTypeName(), chargedMove.getStaminaStatus());
    this.criticalChance = chargedMove.getCriticalChance();
  }

  public ChargedMoveModel(Double criticalChance) {
    this.criticalChance = criticalChance;
  }

  public ChargedMoveModel() {
  }

  public Double getCriticalChance() {
    return criticalChance;
  }

  public void setCriticalChance(Double criticalChance) {
    this.criticalChance = criticalChance;
  }
}
