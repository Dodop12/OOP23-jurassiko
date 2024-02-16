package it.unibo.jurassiko.core.api;

import java.util.Map;
import java.util.Optional;

import it.unibo.jurassiko.common.Pair;
import it.unibo.jurassiko.model.objective.api.Objective;
import it.unibo.jurassiko.model.player.api.Player;
import it.unibo.jurassiko.model.player.api.Player.GameColor;
import it.unibo.jurassiko.model.territory.api.Territory;

/**
 * Interface used to check if any player has completed their objective and set
 * the winning player.
 */
public interface WinCondition {

    Optional<Player> getWinner(Map<Territory, Pair<GameColor, Integer>> territoriesMap,
            Player player,
            Objective objective);

}