package com.pokez.repository;

import com.pokez.configuration.RowMapper.BattlePokemonModelRowMapper;
import com.pokez.configuration.RowMapper.TournamentTrainerModelRowMapper;
import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.battle.TournamentTrainerModel;
import com.pokez.models.catchem.CatchPokemonModel;
import com.pokez.models.catchem.PlanningRequestModel;
import com.pokez.security.models.MainUserModel;
import com.pokez.security.models.UserModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CatchRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public List savePokemonPlanning(MainUserModel user, PlanningRequestModel planning){
    Integer id = jdbcTemplate.queryForObject("SELECT id FROM user_acc WHERE user_name = '" +
          user.getUsername() + "'", Integer.class);
    String sql = "INSERT INTO Catch (user_id, poke_id, caught) "
        + "VALUES (?, (SELECT poke_id FROM pokemon WHERE pokemon_name = ?), false);";
    List<String> pokemonList = new ArrayList<>();
    for (String pokemonName : planning.getPokemonList()){
      Object[] params = {id, pokemonName};
      try {
        jdbcTemplate.update(sql,params);
        pokemonList.add(pokemonName + " was found successfully.");
      }catch (DataIntegrityViolationException e){
        pokemonList.add(pokemonName + " was doesn't found.");
      }
    }
    return pokemonList;
  }

  public void savePokemonCatch(UserModel user, List<CatchPokemonModel> catchList){
    String sql = "INSERT INTO Catch (user_id, poke_id, caught, attack, defense, stamina, speed, "
        + "fast_move, charged_move) VALUES (?,?,?,?,?,?,?,"
        + "(SELECT move_id FROM FastMove WHERE move_name=?),"
        + "(SELECT move_id FROM ChargedMove WHERE move_name=?));";
    List<Object[]> batchParams = new ArrayList<>();
    for (CatchPokemonModel pokemon : catchList){
      batchParams.add(new Object[] {user.getId(),pokemon.getPokeId(),true,pokemon.getAttack(),
      pokemon.getDefense(),pokemon.getStamina(),pokemon.getSpeed(),pokemon.getFastMove(),
      pokemon.getChargedMove()});
    }
    jdbcTemplate.batchUpdate(sql,batchParams);
  }

  public List<BattlePokemonModel> retrieveTeamPokemonByIds(UserModel trainer, List<Integer> pokeList){
    /*
    String sql = "SELECT TP.id_caught_pokemon, TP.poke_id, P.pokemon_name, TP.attack, TP.defense, "
        + "TP.speed, TP.stamina, PFT.type_name AS first_type, PST.type_name AS second_type, "x
        + "F.move_id AS fm_id, F.move_name AS fm_name, F.power AS fm_power, "
        + "F.stamina_loss_scaler AS fm_sls, F.type AS fm_type, "
        + "C.move_id AS cm_id, C.move_name AS cm_name, C.power AS cm_power, "
        + "C.stamina_loss_scaler AS cm_sls, C.type AS cm_type, C.critical_chance AS cm_critic_chance "
        + "FROM Catch AS TP "
        + "INNER JOIN Pokemon AS P ON TP.poke_id=P.poke_id "
        + "INNER JOIN FastMove AS F ON TP.fast_move=F.move_id "
        + "INNER JOIN ChargedMove AS C ON TP.charged_move=C.move_id "
        + "INNER JOIN Type_Effectiveness AS FT ON F.type=FT.type_id "
        + "INNER JOIN Type_Effectiveness AS CT ON C.type=CT.type_id "
        + "INNER JOIN Type_Effectiveness AS PFT ON P.first_type=PFT.type_id "
        + "LEFT JOIN Type_Effectiveness AS PST ON P.second_type=PST.type_id "
        + "WHERE TP.id_caught_pokemon IN (:pokeList) AND TP.user_id=:trainer;";
     */
    String sql ="SELECT TP.id_caught_pokemon, TP.poke_id, P.pokemon_name, TP.attack, TP.defense, TP.speed, TP.stamina, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id=P.first_type) AS first_type, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id=P.second_type) AS second_type, "
        + "F.move_id AS fm_id, F.move_name AS fm_name, F.power AS fm_power, F.stamina_loss_scaler AS fm_sls, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id= F.type) AS fm_type, "
        + "C.move_id AS cm_id, C.move_name AS cm_name, C.power AS cm_power, C.stamina_loss_scaler AS cm_sls, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id= C.type) AS cm_type, "
        + "C.critical_chance AS cm_critic_chance FROM Catch AS TP "
        + "INNER JOIN Pokemon AS P ON TP.poke_id=P.poke_id "
        + "INNER JOIN FastMove AS F ON TP.fast_move=F.move_id "
        + "INNER JOIN ChargedMove AS C ON TP.charged_move=C.move_id "
        + "WHERE TP.id_caught_pokemon IN (:pokeList) AND TP.user_id=:trainer;";
    NamedParameterJdbcTemplate pokeJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    MapSqlParameterSource parameters = new MapSqlParameterSource(
        Map.of("pokeList", pokeList,"trainer",trainer.getId()));
    List<BattlePokemonModel> trainerPokeList = pokeJdbc.query(sql,parameters,
        new BattlePokemonModelRowMapper());
    return trainerPokeList;
  }

  public List<BattlePokemonModel> retrieveTeamPokemonByUserName(UserModel trainer, Integer teamSize){
    String sql = "SELECT TP.id_caught_pokemon, TP.poke_id, P.pokemon_name, TP.attack, TP.defense, TP.speed, TP.stamina, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id=P.first_type) AS first_type, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id=P.second_type) AS second_type, "
        + "F.move_id AS fm_id, F.move_name AS fm_name, F.power AS fm_power, F.stamina_loss_scaler AS fm_sls, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id= F.type) AS fm_type, "
        + "C.move_id AS cm_id, C.move_name AS cm_name, C.power AS cm_power, C.stamina_loss_scaler AS cm_sls, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id= C.type) AS cm_type, "
        + "C.critical_chance AS cm_critic_chance FROM Catch AS TP "
        + "INNER JOIN Pokemon AS P ON TP.poke_id=P.poke_id "
        + "INNER JOIN FastMove AS F ON TP.fast_move=F.move_id "
        + "INNER JOIN ChargedMove AS C ON TP.charged_move=C.move_id "
        + "WHERE TP.user_id=:trainer AND caught = TRUE ORDER BY RANDOM() LIMIT :teamSize;";
    NamedParameterJdbcTemplate pokeJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    MapSqlParameterSource parameters = new MapSqlParameterSource(
        Map.of("trainer", trainer.getId(),"teamSize",teamSize));
    List<BattlePokemonModel> trainerTeam = pokeJdbc.query(sql,parameters,
        new BattlePokemonModelRowMapper());
    return trainerTeam;
  }

  public List<TournamentTrainerModel> retrieveTeamsForTournament(){
    String sql = "SELECT TP.user_id, TP.id_caught_pokemon, TP.poke_id, P.pokemon_name, TP.attack, "
        + "TP.defense, TP.speed, TP.stamina, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id=P.first_type) AS first_type, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id=P.second_type) AS second_type, "
        + "F.move_id AS fm_id, F.move_name AS fm_name, F.power AS fm_power, F.stamina_loss_scaler AS fm_sls, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id= F.type) AS fm_type, "
        + "C.move_id AS cm_id, C.move_name AS cm_name, C.power AS cm_power, C.stamina_loss_scaler AS cm_sls, "
        + "(SELECT type_name FROM Type_Effectiveness WHERE type_id= C.type) AS cm_type, "
        + "C.critical_chance AS cm_critic_chance FROM Catch AS TP "
        + "INNER JOIN Pokemon AS P ON TP.poke_id=P.poke_id "
        + "INNER JOIN FastMove AS F ON TP.fast_move=F.move_id "
        + "INNER JOIN ChargedMove AS C ON TP.charged_move=C.move_id "
        + "WHERE caught = TRUE AND id_caught_pokemon IN (SELECT id_caught_pokemon FROM Catch)"
        + "ORDER BY TP.user_id, RANDOM();";
    NamedParameterJdbcTemplate pokeJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    List<TournamentTrainerModel> trainerTeam = pokeJdbc.query(sql,
        new TournamentTrainerModelRowMapper());
    return trainerTeam;
  }


}
