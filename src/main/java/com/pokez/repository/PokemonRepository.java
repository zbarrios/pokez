package com.pokez.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.catchem.CatchPokemonModel;
import com.pokez.models.catchem.PlanningRequestModel;
import com.pokez.models.pokemon.PokemonModel;
import com.pokez.models.pokemon.StatsModel;
import com.pokez.models.pokemon.TypeModel;
import com.pokez.security.jwt.JwtProvider;
import com.sun.jna.platform.win32.OaIdl.PARAMDESC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  private final static Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);


  public void savePokemonList(LinkedHashMap<String,PokemonModel> pokeList){
    String sql = "INSERT INTO Pokemon (poke_id, pokemon_name) VALUES (?,?) "
        + "ON CONFLICT (poke_id) DO UPDATE SET  pokemon_name = EXCLUDED.pokemon_name;";
    List<Object[]> batchParams = new ArrayList<>();
    for (Entry<String, PokemonModel> entry : pokeList.entrySet()) {
      batchParams.add(new Object[]{entry.getValue().getPokeId(), entry.getValue().getPokemonName()});
    }
    int[] updateCounts = jdbcTemplate.batchUpdate(sql, batchParams);
  }
  @Deprecated
  public void updatePokemonList(Integer pokeId, String pokeName){
    String sql = "UPDATE Pokemon SET pokemon_name = ? WHERE poke_id = ?";
    Object[] params = {pokeName, pokeId};
    jdbcTemplate.update(sql, params);
  }

  public void savePokemonStats(List<StatsModel> statsList){
    String sql = "UPDATE Pokemon SET base_stamina=?, base_defense=?, base_attack=?"
        + " WHERE poke_id=?";
    List<Object[]> batchParams = new ArrayList<>();
    for (StatsModel pokeStats : statsList){
      batchParams.add(new Object[] {pokeStats.getBaseStamina(), pokeStats.getBaseDefense(),
          pokeStats.getBaseAttack(), pokeStats.getPokeId()});
    }
    jdbcTemplate.batchUpdate(sql,batchParams);
  }

  public void savePokemonTypes(List<TypeModel> typesByPoke){
    String sql = "UPDATE Pokemon "
        + "SET first_type=(SELECT type_id FROM type_effectiveness WHERE type_name=?), "
        + "second_type=(SELECT type_id FROM type_effectiveness WHERE type_name=?) "
        + "WHERE poke_id=?;";
    List<Object[]> batchParams = new ArrayList<>();
    for (TypeModel t : typesByPoke) {
      List<String> types = t.getType();
      if (types.size() < 2) types.add(null);
      batchParams.add(new Object[] {types.get(0), types.get(1), t.getPokeId()});
    }
    jdbcTemplate.batchUpdate(sql, batchParams);
  }

  public List<CatchPokemonModel> getPokemonInfo(PlanningRequestModel planning){
    /*
    List<String> pokeNames = planning.getPokemonList();
    String inSql = String.join(",", Collections.nCopies(pokeNames.size(),
        "?"));
    Object[] params = pokeNames.toArray();
    List<BattlePokemonModel> battlePokemonList = jdbcTemplate.query(
        String.format("SELECT * FROM Pokemon WHERE pokemon_name IN (%s)", inSql),
        (bp, rowNum) -> new BattlePokemonModel(
            bp.getInt("poke_id"),
            bp.getString("pokemon_name"),
            bp.getInt("base_attack"),
            bp.getInt("base_defense"),
            (double)bp.getInt("base_stamina"),
            bp.getInt("first_type"),
            bp.getInt("second_type")
        ),params);

         return battlePokemonList;
     */
    List<String> pokeNames = planning.getPokemonList();
    NamedParameterJdbcTemplate pokeJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    MapSqlParameterSource parameters = new MapSqlParameterSource("pokes", pokeNames);
    List<CatchPokemonModel> catchPokemonList = pokeJdbc.query(
        "SELECT * FROM Pokemon WHERE pokemon_name IN (:pokes)",parameters,
        (bp, rowNum) -> new CatchPokemonModel(
            bp.getInt("poke_id"),
            bp.getString("pokemon_name"),
            bp.getInt("base_attack"),
            bp.getInt("base_defense"),
            bp.getInt("base_stamina"),
            bp.getInt("first_type"),
            bp.getInt("second_type")));
    return catchPokemonList;
  }

}
