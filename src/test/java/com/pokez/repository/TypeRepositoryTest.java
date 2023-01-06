package com.pokez.repository;

import com.pokez.PokezApplication;
import com.pokez.models.pokemon.EffectivenessModel;
import com.pokez.repository.mongo.MovesByPokeRepo;
import java.util.LinkedHashMap;
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
class TypeRepositoryTest {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  MovesByPokeRepo movesByPokeRepo;

  @Autowired
  TypeRepository typeRepository;

  @Autowired
  TypeRepositoryTest(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    typeRepository = new TypeRepository();
    ReflectionTestUtils.setField(typeRepository, "jdbcTemplate", jdbcTemplate);
  }


  @Test
  void saveTypeEffectivenessSuccessfullyCase() {
    LinkedHashMap<String, EffectivenessModel> effectivenessList = new LinkedHashMap<>();
    effectivenessList.put("Bug", new EffectivenessModel(1, 2, 3, 4, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1));
    typeRepository.saveTypeEffectiveness(effectivenessList);
    EffectivenessModel actualEffectiveness = jdbcTemplate.queryForObject(
        "SELECT * FROM Type_Effectiveness WHERE type_name = 'Bug'",
        BeanPropertyRowMapper.newInstance(EffectivenessModel.class));
    Assertions.assertEquals(1, actualEffectiveness.getBug());
    Assertions.assertEquals(2, actualEffectiveness.getDark());
    Assertions.assertEquals(3, actualEffectiveness.getDragon());
    Assertions.assertEquals(4, actualEffectiveness.getElectric());
  }

}