package com.pokez.controllers;

import com.pokez.configuration.ResponseHandler;
import com.pokez.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BattleController {

  @Autowired
  ResponseHandler responseHandler;
  @Autowired
  TournamentService tournamentService;

  @GetMapping("trainer-tournament-list")
  public ResponseEntity<Object> testingTournament() {
    return responseHandler.basicObjectResponse(tournamentService.getListOfTrainer(), HttpStatus.OK);
  }

  @GetMapping("trainer-tournament-list2")
  public ResponseEntity<Object> testingTournament2() {
    return responseHandler.basicObjectResponse(tournamentService.setListForTournament(), HttpStatus.OK);
  }

}
