package com.pokez.configuration.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

public class EffectivenessRowMapper implements RowMapper<HashMap<String, HashMap<String,Double>>> {
  @Override
  public HashMap<String, HashMap<String,Double>> mapRow(ResultSet bp, int rowNum) throws SQLException {
    HashMap<String, Double> bpMap = new HashMap<>();
    bpMap.put("Bug", bp.getDouble("bug"));
    bpMap.put("Dark", bp.getDouble("dark"));
    bpMap.put("Dragon", bp.getDouble("dragon"));
    bpMap.put("Electric",bp.getDouble("electric"));
    bpMap.put("Fairy", bp.getDouble("fairy"));
    bpMap.put("Fighting", bp.getDouble("fighting"));
    bpMap.put("Fire", bp.getDouble("fire"));
    bpMap.put("Flying", bp.getDouble("flying"));
    bpMap.put("Ghost", bp.getDouble("ghost"));
    bpMap.put("Grass", bp.getDouble("grass"));
    bpMap.put("Ground", bp.getDouble("ground"));
    bpMap.put("Ice", bp.getDouble("ice"));
    bpMap.put("Normal", bp.getDouble("normal"));
    bpMap.put("Poison", bp.getDouble("poison"));
    bpMap.put("Psychic", bp.getDouble("psychic"));
    bpMap.put("Rock", bp.getDouble("rock"));
    bpMap.put("Steel", bp.getDouble("steel"));
    bpMap.put("Water", bp.getDouble("water"));
    /*HashMap<String,EffectivenessModel> effectiveness2 = new HashMap<>(
        Map.of(bp.getString("type_name"),new EffectivenessModel(
            bp.getDouble("bug"),
            bp.getDouble("dark"),
            bp.getDouble("dragon"),
            bp.getDouble("electric"),
            bp.getDouble("fairy"),
            bp.getDouble("fighting"),
            bp.getDouble("fire"),
            bp.getDouble("flying"),
            bp.getDouble("ghost"),
            bp.getDouble("grass"),
            bp.getDouble("ground"),
            bp.getDouble("ice"),
            bp.getDouble("normal"),
            bp.getDouble("poison"),
            bp.getDouble("psychic"),
            bp.getDouble("rock"),
            bp.getDouble("steel"),
            bp.getDouble("water")),"type_birr", new EffectivenessModel()));

     */
    HashMap<String, HashMap<String,Double>> effectiveness = new HashMap<>(
        Map.of(bp.getString("type_name"),bpMap));
    return effectiveness;
  }
}
