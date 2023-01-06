package com.pokez.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.configuration.ResponseHandler;
import com.pokez.services.MovesByPokeService;
import com.pokez.services.PogoRequestService;
import java.io.IOException;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokemonController {

  @Autowired
  ResponseHandler responseHandler;

  MovesByPokeService movesByPokeService;
  @Autowired
  PogoRequestService pogoRequestService;
  @Autowired
  public PokemonController(MovesByPokeService movesByPokeService) {
    this.movesByPokeService = movesByPokeService;
  }


  @PutMapping("/pokemon-list")
  public ResponseEntity<Object> getPokemonList() throws JsonProcessingException {
    pogoRequestService.getListOfPokemon();
    return responseHandler.basicMessageResponse("Update Pokemon list done successfully",
        HttpStatus.OK);
  }

  @PutMapping("/pokemon-stats")
  public ResponseEntity<Object> getPokemonStats() throws IOException {
    pogoRequestService.getPokemonStats();
    return responseHandler.basicMessageResponse("Update Pokemon stats done successfully",
        HttpStatus.OK);
  }

  @PutMapping("/fast-moves")
  public ResponseEntity<Object> getFastMoves() throws JsonProcessingException {
    pogoRequestService.getFastMoves();
    return responseHandler.basicMessageResponse("Update Fast Moves done successfully",
        HttpStatus.OK);
  }

  @PutMapping("/charged-moves")
  public ResponseEntity<Object> getChargedMoves() throws JsonProcessingException {
    pogoRequestService.getChargedMoves();
    return responseHandler.basicMessageResponse("Update Charged Moves done successfully",
        HttpStatus.OK);
  }

  @PutMapping("/get-type-effectiveness")
  public ResponseEntity<Object> getTypeEffectiveness() throws JsonProcessingException {
    pogoRequestService.getTypesAndEffectiveness();
    return responseHandler
        .basicMessageResponse("Update Types and Effectiveness done successfully",
        HttpStatus.OK);
  }

  @PutMapping("/get-types-by-pokemon")
  public ResponseEntity<Object> getTypesByPokemon() throws JsonProcessingException {
    pogoRequestService.getPokemonTypes();
    return responseHandler.basicMessageResponse("Update Pokemon Types done successfully", HttpStatus.OK);
  }

  //@PreAuthorize("hasRole('ADMIN') or hasRole('ROLE_ADMIN')")
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/moves-by-pokemon")
  public ResponseEntity<Object> getMovesByPokemon() throws JsonProcessingException {
    return responseHandler.basicObjectResponse("Update Moves By Pokemon successfully",HttpStatus.OK);
  }

}
