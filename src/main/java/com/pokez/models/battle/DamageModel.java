package com.pokez.models.battle;

public class DamageModel {

  private String typeMove;
  private Double powerMove;

  public DamageModel(String moveType, Double movePower) {
    this.typeMove = moveType;
    this.powerMove = movePower;
  }

  public DamageModel() {
  }

  public String getTypeMove() {
    return typeMove;
  }

  public void setTypeMove(String typeMove) {
    this.typeMove = typeMove;
  }

  public Double getPowerMove() {
    return powerMove;
  }

  public void setPowerMove(Double powerMove) {
    this.powerMove = powerMove;
  }

}
