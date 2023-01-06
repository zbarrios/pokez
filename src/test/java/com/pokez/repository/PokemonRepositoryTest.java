package com.pokez.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.pokez.PokezApplication;
import com.pokez.models.pokemon.PokemonModel;
import com.pokez.models.pokemon.StatsModel;
import com.pokez.models.pokemon.TypeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
class PokemonRepositoryTest {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  PokemonRepository pokemonRepository;

  @Autowired
  PokemonRepositoryTest(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    pokemonRepository = new PokemonRepository();
    ReflectionTestUtils.setField(pokemonRepository, "jdbcTemplate", jdbcTemplate);
  }

  @BeforeEach
  void setup() {
    jdbcTemplate.execute("INSERT INTO Pokemon (poke_id, pokemon_name) VALUES (1,'pokemonName') "
        + "ON CONFLICT (poke_id) DO UPDATE SET pokemon_name = EXCLUDED.pokemon_name;");
  }

  @Test
  void savePokemonListSuccessfullyCase() {
    LinkedHashMap<String,PokemonModel> mapListTest = new LinkedHashMap<>();
    mapListTest.put("testKey", new PokemonModel("pokemonName",1));
    pokemonRepository.savePokemonList(mapListTest);
    PokemonModel pokemonActual = jdbcTemplate
        .queryForObject("SELECT poke_id, pokemon_name FROM Pokemon WHERE poke_id = 1",
        BeanPropertyRowMapper.newInstance(PokemonModel.class));
    assertEquals("pokemonName",pokemonActual.getPokemonName());
    assertEquals(1,pokemonActual.getPokeId());
  }

  @Test
  void savePokemonStatsSuccessfullyCase() {
    List<StatsModel> statsList = new ArrayList<>(Arrays.asList(
        new StatsModel("pokemonName",1,100,100,100)));
    pokemonRepository.savePokemonStats(statsList);
    StatsModel pokemonActual = jdbcTemplate
        .queryForObject("SELECT poke_id, pokemon_name, base_stamina, base_defense, base_attack "
                + "FROM Pokemon WHERE poke_id=1", BeanPropertyRowMapper.newInstance(StatsModel.class));
    Assertions.assertEquals(1,pokemonActual.getPokeId());
    Assertions.assertEquals("pokemonName",pokemonActual.getPokemonName());
    Assertions.assertEquals(100,pokemonActual.getBaseAttack());
    Assertions.assertEquals(100,pokemonActual.getBaseDefense());
    Assertions.assertEquals(100,pokemonActual.getBaseStamina());
  }

  @Test
  void savePokemonTypesSuccessfullyCase() {
    List<String> types = new ArrayList<>(Arrays.asList("Water"));
    List<TypeModel> typesByPoke = new ArrayList<>(Arrays.asList(
        new TypeModel("pokemonName",1,types)));
    pokemonRepository.savePokemonTypes(typesByPoke);
    Integer firstType = jdbcTemplate.queryForObject("SELECT first_type FROM Pokemon "
        + "WHERE poke_id=1",Integer.class);
    Integer secondType = jdbcTemplate.queryForObject("SELECT second_type FROM Pokemon "
        + "WHERE poke_id=1",Integer.class);
    Assertions.assertNotNull(firstType);
    Assertions.assertNull(secondType);
  }
}