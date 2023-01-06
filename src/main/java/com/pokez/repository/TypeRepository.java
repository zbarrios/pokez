package com.pokez.repository;

import com.pokez.configuration.RowMapper.EffectivenessRowMapper;
import com.pokez.models.pokemon.EffectivenessModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TypeRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public void saveTypeEffectiveness(LinkedHashMap<String, EffectivenessModel> effectivenessList) {
    String sql = "INSERT INTO Type_Effectiveness (type_name, bug, dark, dragon, electric, fairy, "
        + "fighting, fire, flying, ghost, grass, ground, ice, normal, poison, psychic, rock, steel, "
        + "water) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
        + "ON CONFLICT (type_name) DO UPDATE SET bug=EXCLUDED.bug, dark=EXCLUDED.dark, "
        + "dragon=EXCLUDED.dragon, electric=EXCLUDED.electric, fairy=EXCLUDED.fairy, "
        + "fighting=EXCLUDED.fighting, fire=EXCLUDED.fire, flying=EXCLUDED.flying, "
        + "ghost=EXCLUDED.ghost, grass=EXCLUDED.grass, ground=EXCLUDED.ground, "
        + "ice=EXCLUDED.ice, normal=EXCLUDED.normal, poison=EXCLUDED.poison, "
        + "psychic=EXCLUDED.psychic, rock=EXCLUDED.rock, steel=EXCLUDED.steel, "
        + "water=EXCLUDED.water;";
    List<Object[]> batchParams = new ArrayList<>();
    for (Entry<String,EffectivenessModel> entry : effectivenessList.entrySet()){
      batchParams.add(new Object[] {
          entry.getKey(), entry.getValue().getBug(), entry.getValue().getDark(),
          entry.getValue().getDragon(), entry.getValue().getElectric(),
          entry.getValue().getFairy(), entry.getValue().getFighting(), entry.getValue().getFire(),
          entry.getValue().getFlying(), entry.getValue().getGhost(), entry.getValue().getGrass(),
          entry.getValue().getGround(), entry.getValue().getIce(), entry.getValue().getNormal(),
          entry.getValue().getPoison(), entry.getValue().getPsychic(), entry.getValue().getRock(),
          entry.getValue().getSteel(), entry.getValue().getWater()});
    }
    jdbcTemplate.batchUpdate(sql,batchParams);
  }

  @Deprecated
  public void updateTypeEffectiveness(EffectivenessModel effectiveness, String typeName){
    String sql = "UPDATE Type_Effectiveness SET bug=?, dark=?, dragon=?, electric=?, fairy=?,"
        + " fighting=?, fire=?, flying=?, ghost=?, grass=?, ground=?, ice=?, normal=?, poison=?,"
        + " psychic=?, rock=?, steel=?, water=? WHERE type_name=?";
    Object[] params = {
        effectiveness.getBug(),
        effectiveness.getDark(),
        effectiveness.getDragon(),
        effectiveness.getElectric(),
        effectiveness.getFairy(),
        effectiveness.getFighting(),
        effectiveness.getFire(),
        effectiveness.getFlying(),
        effectiveness.getGhost(),
        effectiveness.getGrass(),
        effectiveness.getGround(),
        effectiveness.getIce(),
        effectiveness.getNormal(),
        effectiveness.getPoison(),
        effectiveness.getPsychic(),
        effectiveness.getRock(),
        effectiveness.getSteel(),
        effectiveness.getWater(),
        typeName};
    jdbcTemplate.update(sql,params);
  }

  public HashMap<String,HashMap<String,Double>> getTypeEffectivenessList(){
    String sql = "SELECT * FROM Type_Effectiveness;";
    List<HashMap<String, HashMap<String,Double>>> effectivenessList = jdbcTemplate.query(sql,
        new EffectivenessRowMapper());
    HashMap<String,HashMap<String,Double>> effectiveness = new HashMap<>();
    effectivenessList.forEach(effectiveness::putAll);
    return effectiveness;
  }

}
