package com.pokez.services;

import com.pokez.configuration.GlobalVariable;
import com.pokez.configuration.RandomHandler;
import com.pokez.models.battle.BattlePokemonModel;
import com.pokez.models.battle.ChallengeModel;
import com.pokez.models.battle.DamageModel;
import com.pokez.models.battle.TrainerPokemonModel;
import com.pokez.models.pokemon.moves.ChargedMoveModel;
import com.pokez.models.pokemon.moves.FastMoveModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleService {

  @Autowired
  CatchService catchService;
  @Autowired
  RandomHandler randomHandler;
  @Autowired
  TypeService typeService;


  public TrainerPokemonModel oneVersusOne(ChallengeModel challenge){
    List<Integer> pokemonTeam = challenge.getPokemonTeam().stream().mapToInt(Integer::parseInt)
        .boxed().collect(Collectors.toList());
    TrainerPokemonModel trainerOne = catchService.getTrainerTeamByIds(pokemonTeam);
    TrainerPokemonModel trainerTwo = catchService.getTrainerTeamByUser(challenge.getTrainerTwo());
    if (trainerTwo.getPokemonList().size()<1) return trainerOne;
    return teamBattle(trainerOne,trainerTwo);
  }

  public TrainerPokemonModel teamBattle(TrainerPokemonModel trainerOne,
      TrainerPokemonModel trainerTwo) {
    int i = 0, j = 0;
    List<BattlePokemonModel> teamTrainerOne = new ArrayList<>(trainerOne.getPokemonList());
    List<BattlePokemonModel> teamTrainerTwo = new ArrayList<>(trainerTwo.getPokemonList());
    while (i < teamTrainerOne.size() && j < teamTrainerTwo.size()) {
      BattlePokemonModel winnerPokemon = battlePokemon(new BattlePokemonModel(teamTrainerOne.get(i)),
          new BattlePokemonModel(teamTrainerTwo.get(j)));
      if (winnerPokemon.getPokemonCatchId().equals(teamTrainerOne.get(i).getPokemonCatchId())) j++;
      else i++;
      // matchService.filterMatchInformation(teamTrainerOne.get(i), teamTrainerTwo.get(j));
      // matchService.filterMatchInformation(teamTrainerTwo.get(j), teamTrainerOne.get(i));
    }
    if (i<j) return trainerOne;
    return trainerTwo;
  }

  /**
   * Service that iterates a battle until a winner is found.

  public BattlePokemonModel battlePokemon2(BattlePokemonModel fasterPokemon,
      BattlePokemonModel slowerPokemon) {
    calculateHitDamage(
        calculateMoveDamage(fasterPokemon.getFastMove(), fasterPokemon.getChargedMove()),
        fasterPokemon, slowerPokemon);
    if (slowerPokemon.getStamina() <= 0) {
      return fasterPokemon;
    }
    calculateHitDamage(
        calculateMoveDamage(slowerPokemon.getFastMove(), slowerPokemon.getChargedMove()),
        slowerPokemon, fasterPokemon);
    if (fasterPokemon.getStamina() <= 0) {
      return slowerPokemon;
    }
    return battlePokemon(fasterPokemon, slowerPokemon);
  }
   */
  /**
   * Service that iterates a battle until a winner is found.
   */

  public BattlePokemonModel battlePokemon(BattlePokemonModel pokemonOne,
      BattlePokemonModel pokemonTwo) {
    pokemonOne.setChosenMove(randomSelectMove(pokemonOne));
    pokemonTwo.setChosenMove(randomSelectMove(pokemonTwo));
    BattlePokemonModel fasterPokemon = getFasterPokemon(pokemonOne,pokemonTwo);
    BattlePokemonModel slowerPokemon = fasterPokemon.getPokemonCatchId()
        .equals(pokemonOne.getPokemonCatchId())?pokemonTwo:pokemonOne;
    calculateHitDamage(calculateMoveDamage(fasterPokemon),fasterPokemon,slowerPokemon);
    if (slowerPokemon.getStamina() <= 0) return fasterPokemon;
    calculateHitDamage(calculateMoveDamage(slowerPokemon),slowerPokemon,fasterPokemon);
    if (fasterPokemon.getStamina() <= 0) return slowerPokemon;
    return battlePokemon(fasterPokemon, slowerPokemon);
  }

  /**
   * Service that sends Pokemon into battle based on their speed stat.
   */
  public BattlePokemonModel getFasterPokemon(BattlePokemonModel pokemonOne,
      BattlePokemonModel pokemonTwo) {
    Integer pOneChoice = pokemonOne.getChosenMove()>0?pokemonOne.getChosenMove():1;
    Integer pTwoChoice = pokemonTwo.getChosenMove()>0?pokemonTwo.getChosenMove():1;
    if(pokemonOne.getSpeed()/pOneChoice>=
        pokemonTwo.getSpeed()/pTwoChoice){
      return pokemonOne;
    }
    return pokemonTwo;
  }

  /**
   * Service that calculate the total damage by attack.
   */
  public void calculateHitDamage(DamageModel damageMove, BattlePokemonModel attackerPokemon,
      BattlePokemonModel defenderPokemon) {
    Double effectiveness = typeService.calculateEffectiveness(damageMove,defenderPokemon);
    Double totalDamage = (0.5 * damageMove.getPowerMove()
        * attackerPokemon.getAttack() / (defenderPokemon.getDefense() + 1) * effectiveness) + 1;
    defenderPokemon.setStamina(defenderPokemon.getStamina() - totalDamage);
  }

  /**
   * Service that choose a random move and get his damage from a poke.
   */
  public DamageModel calculateMoveDamage(BattlePokemonModel pokemon) {
    DamageModel damage = new DamageModel();
    switch (pokemon.getChosenMove()) {
      case 1: {
        FastMoveModel fastMove = pokemon.getFastMove();
        fastMove.setStaminaStatus(fastMove.getStaminaStatus()
            - GlobalVariable.MOVE_STAMINA * fastMove.getStaminaLossScale());
        damage.setPowerMove(fastMove.getPower());
        damage.setTypeMove(fastMove.getTypeName());
        return damage;
      }
      case 2: {
        ChargedMoveModel chargedMove = pokemon.getChargedMove();
        chargedMove.setStaminaStatus(chargedMove.getStaminaStatus()
            - GlobalVariable.MOVE_STAMINA * chargedMove.getStaminaLossScale());
        damage.setTypeMove(chargedMove.getTypeName());
        if (randomHandler.randomDouble() <= chargedMove.getCriticalChance()) {
          damage.setPowerMove(chargedMove.getPower() * GlobalVariable.CRITICAL_DAMAGE);
          return damage;
        }
        damage.setPowerMove(chargedMove.getPower());
        return damage;
      }
      default: {
        damage.setPowerMove(50.00);
        damage.setTypeMove("Normal");
        return damage;
      }
    }
  }

  public int randomSelectMove(BattlePokemonModel pokemon) {
    int choice;
    FastMoveModel fastMove = pokemon.getFastMove();
    ChargedMoveModel chargedMove = pokemon.getChargedMove();
    if (fastMove.getStaminaStatus() > 0.00 && chargedMove.getStaminaStatus() > 0.00)
      choice = randomHandler.randomInteger(2)+1;
     else if (fastMove.getStaminaStatus() > 0.00)  choice = 1;
     else if (chargedMove.getStaminaStatus() > 0.00)  choice = 2;
     else choice = 0;
    return choice;
  }

}
