package com.pokez.models.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Model for Pokemon Type.
 */
@JsonIgnoreProperties({"form"})
public class TypeModel extends PokemonModel {

  @JsonProperty("type")
  private List<String> type;

  /**
   * Constructor for Pokemon Type Model.
   */
  public TypeModel(String name, Integer id, List<String> type) {
    super(name, id);
    this.type = type;
  }

  public TypeModel() {
  }

  public List<String> getType() {
    return type;
  }

  public void setType(List<String> type) {
    this.type = type;
  }
}

