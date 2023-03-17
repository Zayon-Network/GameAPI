package de.nehlen.gameapi.PhaseApi;

import lombok.Getter;
import lombok.Setter;

public interface GamePhase {
    int counter = 0;
    int scheduler = 0;

    void setCounter(int counter);

    int getCounter();

    void startPhase();

    void endPhase();
}
