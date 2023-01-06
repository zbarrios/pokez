package com.pokez.models.pokemon.moves;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for get the Fast Moves from Pogo Api Response.
 */
@JsonIgnoreProperties({"duration", "energy_delta", "heal_scalar"})
public class FastMoveModel {

  @JsonProperty("move_id")
  private Integer moveId;
  @JsonProperty("move_name")
  @JsonAlias({"move_name","name"})
  private String moveName;
  @JsonProperty("power")
  private Double power;
  @JsonProperty("stamina_loss_scaler")
  private Double staminaLossScale;
  @JsonProperty("type_name")
  @JsonAlias({"type_name","type"})
  private String typeName;
  private Double staminaStatus;

  /**
   * Constructor for FastMoveModel.
   */
  public FastMoveModel(Integer moveId, String moveName, Double power, Double staminaLossScale,
      String typeName, Double staminaStatus) {
    this.moveId = moveId;
    this.moveName = moveName;
    this.power = power;
    this.staminaLossScale = staminaLossScale;
    this.typeName = typeName;
    this.staminaStatus = staminaStatus;
  }

  public FastMoveModel(FastMoveModel fastMove) {
    this.moveId = fastMove.getMoveId();
    this.moveName = fastMove.getMoveName();
    this.power = fastMove.getPower();
    this.staminaLossScale = fastMove.getStaminaLossScale();
    this.typeName = fastMove.typeName;
    this.staminaStatus = fastMove.staminaStatus;
  }

  public FastMoveModel() {
  }

  public Integer getMoveId() {
    return moveId;
  }

  public void setMoveId(Integer moveId) {
    this.moveId = moveId;
  }

  public String getMoveName() {
    return moveName;
  }

  public void setMoveName(String moveName) {
    this.moveName = moveName;
  }

  public Double getPower() {
    return power;
  }

  public void setPower(Double power) {
    this.power = power;
  }

  public Double getStaminaLossScale() {
    return staminaLossScale;
  }

  public void setStaminaLossScale(Double staminaLossScale) {
    this.staminaLossScale = staminaLossScale;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Double getStaminaStatus() {
    return staminaStatus;
  }

  public void setStaminaStatus(Double staminaStatus) {
    this.staminaStatus = staminaStatus;
  }
}
