package com.pokez.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokez.models.pokemon.CurrentMovesModel;
import com.pokez.services.MovesByPokeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@RunWith(SpringRunner.class)
@WithMockUser(username = "adminUser", roles = {"ADMIN", "USER"})
@AutoConfigureMockMvc
class PokemonControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  MovesByPokeService movesByPokeService;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  PokemonController pokemonController;


  @Test
  void getPokemonListSuccessfullyCase() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/pokemon-list")).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String, Object> mapResponse = objectMapper.readValue(response,new TypeReference<>() {});
    Assertions.assertEquals("Update Pokemon list done successfully",mapResponse.get("message"));
  }

  @Test
  void getPokemonStats() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/pokemon-stats")).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals(
        "Update Pokemon stats done successfully",mapResponse.get("message"));
  }

  @Test
  void getFastMoves() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/fast-moves")).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Update Fast Moves done successfully",mapResponse.get("message"));
  }

  @Test
  void getChargedMoves() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/charged-moves")).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals("Update Charged Moves done successfully",mapResponse.get("message"));
  }

  @Test
  void getTypeEffectiveness() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/get-type-effectiveness")).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals(
        "Update Types and Effectiveness done successfully",mapResponse.get("message"));
  }

  @Test
  void getTypesByPokemon() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/get-types-by-pokemon")).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    Map<String,Object> mapResponse = objectMapper.readValue(response, new TypeReference<>() {});
    Assertions.assertEquals(
        "Update Pokemon Types done successfully",mapResponse.get("message"));
  }



}