package com.pokez.models.pokemon;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for get the Moves from Pogo Api Response.
 */
@JsonIgnoreProperties({"elite_charged_moves", "elite_fast_moves"})
@Document
public class CurrentMovesModel extends PokemonModel {

  @JsonProperty("fast_moves")
  private List<String> fastMoves;
  @JsonProperty("charged_moves")
  private List<String> chargedMoves;
  @JsonProperty("form")
  private String form;
  @Id
  private String serialId;

  /**
   * Constructor for CurrentMovesModel.
   */
  public CurrentMovesModel(String pokemonName, Integer pokeId, List<String> fastMoves,
      List<String> chargedMoves, String form, String serialId) {
    super(pokemonName, pokeId);
    this.fastMoves = fastMoves;
    this.chargedMoves = chargedMoves;
    this.form = form;
    this.serialId = serialId;
  }

  @Override
  public String toString() {
    return "CurrentMovesModel{" +
        "fastMoves=" + fastMoves +
        ", chargedMoves=" + chargedMoves +
        ", form='" + form + '\'' +
        ", serialId='" + serialId + '\'' +
        '}';
  }

  public CurrentMovesModel() {
  }

  public String getSerialId() {
    return serialId;
  }

  public void setSerialId(String serialId) {
    this.serialId = serialId;
  }

  public List<String> getFastMoves() {
    return fastMoves;
  }

  public void setFastMoves(List<String> fastMoves) {
    this.fastMoves = fastMoves;
  }

  public List<String> getChargedMoves() {
    return chargedMoves;
  }

  public void setChargedMoves(List<String> chargedMoves) {
    this.chargedMoves = chargedMoves;
  }

  public String getForm() {
    return form;
  }

  public void setForm(String form) {
    this.form = form;
  }
}
