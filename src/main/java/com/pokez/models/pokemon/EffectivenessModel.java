package com.pokez.models.pokemon;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for Type Effectiveness Model.
 */
public class EffectivenessModel {

  @JsonProperty("Bug")
  private double bug;
  @JsonProperty("Dark")
  private double dark;
  @JsonProperty("Dragon")
  private double dragon;
  @JsonProperty("Electric")
  private double electric;
  @JsonProperty("Fairy")
  private double fairy;
  @JsonProperty("Fighting")
  private double fighting;
  @JsonProperty("Fire")
  private double fire;
  @JsonProperty("Flying")
  private double flying;
  @JsonProperty("Ghost")
  private double ghost;
  @JsonProperty("Grass")
  private double grass;
  @JsonProperty("Ground")
  private double ground;
  @JsonProperty("Ice")
  private double ice;
  @JsonProperty("Normal")
  private double normal;
  @JsonProperty("Poison")
  private double poison;
  @JsonProperty("Psychic")
  private double psychic;
  @JsonProperty("Rock")
  private double rock;
  @JsonProperty("Steel")
  private double steel;
  @JsonProperty("Water")
  private double water;

  /**
   * Constructor for Type Model.
   */
  public EffectivenessModel(double bug, double dark, double dragon, double electric,
      double fairy,
      double fighting, double fire, double flying, double ghost, double grass, double ground,
      double ice, double normal, double poison, double psychic, double rock, double steel,
      double water) {
    this.bug = bug;
    this.dark = dark;
    this.dragon = dragon;
    this.electric = electric;
    this.fairy = fairy;
    this.fighting = fighting;
    this.fire = fire;
    this.flying = flying;
    this.ghost = ghost;
    this.grass = grass;
    this.ground = ground;
    this.ice = ice;
    this.normal = normal;
    this.poison = poison;
    this.psychic = psychic;
    this.rock = rock;
    this.steel = steel;
    this.water = water;
  }

  @Override
  public String toString() {
    return "EffectivenessModel{" +
        "bug=" + bug +
        ", dark=" + dark +
        ", dragon=" + dragon +
        ", electric=" + electric +
        ", fairy=" + fairy +
        ", fighting=" + fighting +
        ", fire=" + fire +
        ", flying=" + flying +
        ", ghost=" + ghost +
        ", grass=" + grass +
        ", ground=" + ground +
        ", ice=" + ice +
        ", normal=" + normal +
        ", poison=" + poison +
        ", psychic=" + psychic +
        ", rock=" + rock +
        ", steel=" + steel +
        ", water=" + water +
        '}';
  }

  public EffectivenessModel() {
  }

  public double getBug() {
    return bug;
  }

  public void setBug(double bug) {
    this.bug = bug;
  }

  public double getDark() {
    return dark;
  }

  public void setDark(double dark) {
    this.dark = dark;
  }

  public double getDragon() {
    return dragon;
  }

  public void setDragon(double dragon) {
    this.dragon = dragon;
  }

  public double getElectric() {
    return electric;
  }

  public void setElectric(double electric) {
    this.electric = electric;
  }

  public double getFairy() {
    return fairy;
  }

  public void setFairy(double fairy) {
    this.fairy = fairy;
  }

  public double getFighting() {
    return fighting;
  }

  public void setFighting(double fighting) {
    this.fighting = fighting;
  }

  public double getFire() {
    return fire;
  }

  public void setFire(double fire) {
    this.fire = fire;
  }

  public double getFlying() {
    return flying;
  }

  public void setFlying(double flying) {
    this.flying = flying;
  }

  public double getGhost() {
    return ghost;
  }

  public void setGhost(double ghost) {
    this.ghost = ghost;
  }

  public double getGrass() {
    return grass;
  }

  public void setGrass(double grass) {
    this.grass = grass;
  }

  public double getGround() {
    return ground;
  }

  public void setGround(double ground) {
    this.ground = ground;
  }

  public double getIce() {
    return ice;
  }

  public void setIce(double ice) {
    this.ice = ice;
  }

  public double getNormal() {
    return normal;
  }

  public void setNormal(double normal) {
    this.normal = normal;
  }

  public double getPoison() {
    return poison;
  }

  public void setPoison(double poison) {
    this.poison = poison;
  }

  public double getPsychic() {
    return psychic;
  }

  public void setPsychic(double psychic) {
    this.psychic = psychic;
  }

  public double getRock() {
    return rock;
  }

  public void setRock(double rock) {
    this.rock = rock;
  }

  public double getSteel() {
    return steel;
  }

  public void setSteel(double steel) {
    this.steel = steel;
  }

  public double getWater() {
    return water;
  }

  public void setWater(double water) {
    this.water = water;
  }
}