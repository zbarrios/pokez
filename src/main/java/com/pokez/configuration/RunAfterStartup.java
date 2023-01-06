package com.pokez.configuration;

import com.pokez.repository.TypeRepository;
import com.pokez.services.BattleService;
import com.pokez.services.PogoRequestService;
import com.pokez.services.TypeService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class RunAfterStartup {

  @Autowired
  PogoRequestService pogoRequestService;
  @Autowired
  TypeService typeService;

  @EventListener(ApplicationReadyEvent.class)
  public void runAfterStartup() throws IOException {
    pogoRequestService.getListOfPokemon();
    pogoRequestService.getPokemonStats();
    pogoRequestService.getTypesAndEffectiveness();
    pogoRequestService.getPokemonTypes();
    pogoRequestService.getFastMoves();
    pogoRequestService.getChargedMoves();
    pogoRequestService.getCurrentMovesByPokemon();
  }

  @EventListener
  public void runAfterTypeUpdate(TypeRepositoryEvent event) {

  }

}
