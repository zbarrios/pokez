package com.pokez.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokez.configuration.ResponseHandler;
import com.pokez.models.battle.ChallengeModel;
import com.pokez.models.catchem.PlanningRequestModel;
import com.pokez.services.BattleService;
import com.pokez.services.CatchService;
import java.util.Arrays;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatchController {

  @Autowired
  CatchService catchService;
  @Autowired
  ResponseHandler responseHandler;

  @Autowired
  BattleService battleService;

  @PutMapping("/get-pokemon-info")
  public ResponseEntity<Object> getPokemonInfo(@RequestBody @Valid PlanningRequestModel planningRequestModel){
    return responseHandler.basicObjectResponse(catchService.savePokemonPlanning(planningRequestModel),HttpStatus.OK);
  }

  @PostMapping("/pokemon-catch")
  public ResponseEntity<Object> pokemonCatch(@RequestBody @Valid PlanningRequestModel planningRequestModel)
      throws JsonProcessingException {
    return responseHandler.basicObjectResponse(catchService.savePokemonCatch(planningRequestModel),HttpStatus.OK);
  }

  @GetMapping("/trainer-pokemon-list")
  public ResponseEntity<Object> pokemonCatch2(@RequestBody @Valid ChallengeModel challenge)
      throws JsonProcessingException {
    return responseHandler.basicObjectResponse(battleService.oneVersusOne(challenge),HttpStatus.OK);
  }

}
