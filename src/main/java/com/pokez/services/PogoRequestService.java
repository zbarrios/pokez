package com.pokez.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokez.models.pokemon.CurrentMovesModel;
import com.pokez.repository.PokemonRepository;
import com.pokez.repository.TypeRepository;
import com.pokez.repository.MoveRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PogoRequestService {

  @Autowired
  RestTemplate restTemplate;
  @Autowired
  PokemonRepository pokemonRepository;
  @Autowired
  MoveRepository moveRepository;
  @Autowired
  TypeRepository typeRepository;
  @Autowired
  TypeService typeService;
  @Autowired
  MovesByPokeService movesByPokeService;

  /**
   * Method to make a pogo request.
   */
  public <T> ResponseEntity<T> requestPogo(String uri, HttpMethod method, Class<T> clazz,
      Map<String, String> variables) {
    String finalUrl = "https://pogoapi.net" + uri;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
    HttpEntity<String> entity = new HttpEntity<>("", headers);
    return restTemplate.exchange(finalUrl, method, entity, clazz, variables);
  }

  /**
   * Method to get a list of pokemon.
   * The principal method to map is:
   * LinkedHashMap<String,PokemonModel> pokeList = mapper.readValue(json.getBody(), new TypeReference<>() {});
   */
  public void getListOfPokemon()
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/api/v1/pokemon_names.json";
    ResponseEntity<String> json = requestPogo(uri, HttpMethod.GET, String.class, new HashMap<>());
    pokemonRepository.savePokemonList(mapper.readValue(json.getBody(), new TypeReference<>() {}));
  }

  /**
   * Method to get Stats of pokemon.
   */
  public void getPokemonStats() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/api/v1/pokemon_stats.json";
    ResponseEntity<String> json = requestPogo(uri, HttpMethod.GET, String.class, new HashMap<>());
    pokemonRepository.savePokemonStats(mapper.readValue(json.getBody(), new TypeReference<>() {}));
  }

  /**
   * Method to get fast moves from pogo api.
   * Map a List of FastMoves
   */
  public void getFastMoves() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/api/v1/fast_moves.json";
    ResponseEntity<String> json = requestPogo(uri, HttpMethod.GET, String.class, new HashMap<>());
    moveRepository.saveListOfFastMoves(mapper.readValue(json.getBody(), new TypeReference<>() {}));
  }

  /**
   * Method to get charged moves from pogo api.
   * Map a List of ChargedMoves
   */
  public void getChargedMoves()
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/api/v1/charged_moves.json";
    ResponseEntity<String> json = requestPogo(uri, HttpMethod.GET, String.class, new HashMap<>());
    moveRepository.saveListOfChargedMoves(mapper.readValue(json.getBody(), new TypeReference<>() {}));
  }

  /**
   * Method to get current moves by poke from pogo api.
   * Map a List of CurrentMovesModel
   */
  public void getCurrentMovesByPokemon()
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/api/v1/current_pokemon_moves.json";
    ResponseEntity<String> json = requestPogo(uri, HttpMethod.GET, String.class, new HashMap<>());
    movesByPokeService.
        insertListOfMovesByPokemon(mapper.readValue(json.getBody(), new TypeReference<>() {}));
  }


  /**
   * Method to get type effectiveness from pogo api.
   *
   */
  public void getTypesAndEffectiveness()
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/api/v1/type_effectiveness.json";
    ResponseEntity<String> json = requestPogo(uri, HttpMethod.GET, String.class, new HashMap<>());
    typeRepository.saveTypeEffectiveness(mapper.readValue(json.getBody(),new TypeReference<>() {}));
    typeService.updateEffectiveness();
  }

  /**
   * Method to get pokemon types from pogo api.
   * Map a List of TypesModel
   */
  public void getPokemonTypes()
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/api/v1/pokemon_types.json";
    ResponseEntity<String> json = requestPogo(uri, HttpMethod.GET, String.class, new HashMap<>());
    pokemonRepository.savePokemonTypes(mapper.readValue(json.getBody(), new TypeReference<>() {}));
  }





}
