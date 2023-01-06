package com.pokez.repository;

import com.pokez.PokezApplication;
import com.pokez.models.pokemon.moves.ChargedMoveModel;
import com.pokez.models.pokemon.moves.FastMoveModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PokezApplication.class)
class MoveRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private MoveRepository moveRepository;

  @Autowired
  MoveRepositoryTest(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate = jdbcTemplate;
    moveRepository = new MoveRepository();
    ReflectionTestUtils.setField(moveRepository, "jdbcTemplate", jdbcTemplate);
  }

  @BeforeEach
  void setup() {
  }


  @Test
  void saveListOfFastWhenTheseAlreadyExist() {
    jdbcTemplate.execute("INSERT INTO FastMove (move_id, move_name, power, "
        + "stamina_loss_scaler, type) VALUES (1000, 'Fast Thunder', 10, 0.025, "
        + "(SELECT type_id FROM Type_Effectiveness WHERE type_name='Electric'))"
        + "ON CONFLICT(move_id) DO NOTHING;");
    FastMoveModel fastMoveModel = new FastMoveModel(1000,"Fast Thunder",
        1.0,0.5, "Electric", 1.0);
    List<FastMoveModel> fastMoves = new ArrayList<>(Arrays.asList(fastMoveModel));
    moveRepository.saveListOfFastMoves(fastMoves);
    FastMoveModel actualFastMove = jdbcTemplate.queryForObject(
        "SELECT F.move_id, F.move_name, F.power, F.stamina_loss_scaler, T.type_name"
            + " FROM FastMove AS F INNER JOIN Type_Effectiveness AS T ON F.type = T.type_id"
            + " WHERE move_name = 'Fast Thunder'",
        BeanPropertyRowMapper.newInstance(FastMoveModel.class));
    Assertions.assertEquals(fastMoveModel.getMoveName(),actualFastMove.getMoveName());
    Assertions.assertEquals(fastMoveModel.getTypeName(),actualFastMove.getTypeName());
    Assertions.assertEquals(fastMoveModel.getPower(),actualFastMove.getPower());
  }


  @Test
  void saveListOfChargedWhenChargedMoveAlreadyExists() {
    jdbcTemplate.execute("INSERT INTO ChargedMove (move_id, move_name, power, "
        + "stamina_loss_scaler, type, critical_chance) VALUES (2000, 'Charged Thunder', 100, 0.25, "
        + "(SELECT type_id FROM Type_Effectiveness WHERE type_name='Electric'), 0.5)"
        + "ON CONFLICT(move_id) DO NOTHING;");
    ChargedMoveModel chargedMoveTest = new ChargedMoveModel(2000,"Charged Thunder",
        200.0,0.5, "Electric", 1.0,0.5);
    List<ChargedMoveModel> chargedMoves = new ArrayList<>(Arrays.asList(chargedMoveTest));
    moveRepository.saveListOfChargedMoves(chargedMoves);
    ChargedMoveModel actualChargedMove = jdbcTemplate.queryForObject(
        "SELECT C.move_id, C.move_name, C.power, C.stamina_loss_scaler, T.type_name, "
            + "C.critical_chance FROM ChargedMove AS C INNER JOIN Type_Effectiveness AS T "
            + "ON C.type = T.type_id WHERE move_name = 'Charged Thunder'",
        BeanPropertyRowMapper.newInstance(ChargedMoveModel.class));
    Assertions.assertEquals(chargedMoveTest.getMoveName(),actualChargedMove.getMoveName());
    Assertions.assertEquals(chargedMoveTest.getTypeName(),actualChargedMove.getTypeName());
    Assertions.assertEquals(chargedMoveTest.getCriticalChance(),actualChargedMove.getCriticalChance());
    Assertions.assertEquals(chargedMoveTest.getPower(),actualChargedMove.getPower());
  }

}