package com.pokez.repository;

import com.pokez.models.pokemon.moves.FastMoveModel;
import com.pokez.models.pokemon.moves.ChargedMoveModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MoveRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  /**
   * Explanation
   * @param moveList
   * This is equal method to used 'for i'.
   * Example:
   * for (i=0; i<moveList.size; i++){
   *   moveList.get(i).getName;
   * }
   * for (FastMoveModel fastMove: moveList){
   *    fastMove.getName;
   * }
   * You can iterate every index of the list. Is very usefully if you are interested in each object.
   */
  public void saveListOfFastMoves(List<FastMoveModel> moveList){
    String sql = "INSERT INTO FastMove (move_id, move_name, power, stamina_loss_scaler, type) "
        + "VALUES (?, ?, ?, ?, (SELECT type_id FROM Type_Effectiveness WHERE type_name=?)) "
        + "ON CONFLICT (move_id) DO UPDATE SET move_name=EXCLUDED.move_name, power=EXCLUDED.power, "
        + "stamina_loss_scaler=EXCLUDED.stamina_loss_scaler, type=EXCLUDED.type;";
    List<Object[]> batchParams = new ArrayList<>();
    for (FastMoveModel fastMove: moveList) {
      batchParams.add(new Object[] {fastMove.getMoveId(), fastMove.getMoveName(),
          fastMove.getPower(), fastMove.getStaminaLossScale(), fastMove.getTypeName()});
    }
    jdbcTemplate.batchUpdate(sql,batchParams);
  }

  @Deprecated
  public void updateFasMove(FastMoveModel fastMove){
    String sql = "UPDATE FastMove SET move_name=?, power=?, stamina_loss_scaler=?, "
        + "type=(SELECT type_id FROM Type_Effectiveness WHERE type_name=?) WHERE move_id=?";
    Object[] params= {fastMove.getMoveName(), fastMove.getPower(), fastMove.getStaminaLossScale(),
        fastMove.getTypeName(), fastMove.getMoveId()};
    jdbcTemplate.update(sql,params);
  }

  public void saveListOfChargedMoves(List<ChargedMoveModel> moveList){
    String sql = "INSERT INTO ChargedMove (move_id, move_name, power, stamina_loss_scaler, type, "
        + "critical_chance) VALUES (?, ?, ?, ?, "
        + "(SELECT type_id FROM Type_Effectiveness WHERE type_name=?), ?) "
        + "ON CONFLICT (move_id) DO UPDATE SET move_name=EXCLUDED.move_name, power=EXCLUDED.power, "
        + "stamina_loss_scaler=EXCLUDED.stamina_loss_scaler, type=EXCLUDED.type, "
        + "critical_chance=EXCLUDED.critical_chance;";
    List<Object[]> batchParams = new ArrayList<>();
    for (ChargedMoveModel chargedMove : moveList) {
      if (chargedMove.getCriticalChance()==null) chargedMove.setCriticalChance(0.00);
      batchParams.add(new Object[] { chargedMove.getMoveId(), chargedMove.getMoveName(),
          chargedMove.getPower(), chargedMove.getStaminaLossScale(), chargedMove.getTypeName(),
          chargedMove.getCriticalChance()});
    }
    jdbcTemplate.batchUpdate(sql,batchParams);
  }

  @Deprecated
  public void updateChargedMove(ChargedMoveModel chargedMove){
    String sql = "UPDATE ChargedMove SET move_name=?, power=?, stamina_loss_scaler=?, "
        + "type=(SELECT type_id FROM Type_Effectiveness WHERE type_name=?), critical_chance=? "
        + "WHERE move_id=?;";
    Object[] params= {chargedMove.getMoveName(), chargedMove.getPower(),
        chargedMove.getStaminaLossScale(), chargedMove.getTypeName(),
        chargedMove.getCriticalChance(), chargedMove.getMoveId()};
    jdbcTemplate.update(sql,params);
  }

}
