package com.pokez.configuration.RowMapper;

import com.pokez.configuration.GlobalVariable;
import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.battle.TournamentTrainerModel;
import com.pokez.models.pokemon.moves.ChargedMoveModel;
import com.pokez.models.pokemon.moves.FastMoveModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TournamentTrainerModelRowMapper implements RowMapper<TournamentTrainerModel> {
  @Override
  public TournamentTrainerModel mapRow(ResultSet bp, int rowNum) throws SQLException {
    return new TournamentTrainerModel(
        bp.getInt("user_id"),
        new BattlePokemonModel(
        bp.getInt("id_caught_pokemon"),
        bp.getInt("poke_id"),
        bp.getString("pokemon_name"),
        bp.getInt("attack"),
        bp.getInt("defense"),
        bp.getInt("speed"),
        bp.getDouble("stamina"),
        bp.getString("first_type"),
        bp.getString("second_type"),
        new FastMoveModel(
            bp.getInt("fm_id"), bp.getString("fm_name"),
            bp.getDouble("fm_power"), bp.getDouble("fm_sls"),
            bp.getString("fm_type"), GlobalVariable.MOVE_STAMINA),
        new ChargedMoveModel(
            bp.getInt("cm_id"), bp.getString("cm_name"),
            bp.getDouble("cm_power"), bp.getDouble("cm_sls"),
            bp.getString("cm_type"), GlobalVariable.MOVE_STAMINA,
            bp.getDouble("cm_critic_chance"))
        )
    );
  }
}
