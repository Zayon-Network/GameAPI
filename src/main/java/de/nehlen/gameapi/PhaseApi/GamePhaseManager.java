package de.nehlen.gameapi.PhaseApi;

import de.nehlen.gameapi.PhaseApi.exceptions.GamePhaseLastException;
import de.nehlen.gameapi.PhaseApi.exceptions.GamePhaseNotRegisteredException;

import java.util.HashMap;
import java.util.Map;

public class GamePhaseManager {

    private Map<String, GamePhase> gamePhases = new HashMap<>();

    public void registerPhase(String name, GamePhase gamePhase) {
        gamePhases.put(name, gamePhase);
    }

    public void startPhase(String name, int count) {
        GamePhase gamePhase = gamePhases.get(name);
        if (gamePhase != null) {
            gamePhase.setCounter(count);
            gamePhase.startPhase();
        } else {
            throw new GamePhaseNotRegisteredException("Game Phase with name " + name + " is not registered.");
        }
    }

    public void endPhase(String name) {
        GamePhase gamePhase = gamePhases.get(name);
        if (gamePhase != null) {
            gamePhase.endPhase();
        } else {
            throw new GamePhaseNotRegisteredException("Game Phase with name " + name + " is not registered.");
        }
    }

    public void continueToNextPhase() {
        if(gamePhases.size() <= 1) {
            throw new GamePhaseLastException("No Game Phase to continue to.");
        }
        Map.Entry<String, GamePhase> currentGamePhase = gamePhases.entrySet().iterator().next();
        currentGamePhase.getValue().endPhase();
        gamePhases.remove(currentGamePhase.getKey());

        Map.Entry<String, GamePhase> newCurrentPhase = gamePhases.entrySet().iterator().next();
        newCurrentPhase.getValue().startPhase();
    }
}
