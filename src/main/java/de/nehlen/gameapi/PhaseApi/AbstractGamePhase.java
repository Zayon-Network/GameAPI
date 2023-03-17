package de.nehlen.gameapi.PhaseApi;

import de.nehlen.gameapi.Gameapi;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

public abstract class AbstractGamePhase implements GamePhase {

    @Getter @Setter
    protected int counter = 0;
    protected int scheduler = 0;

    public AbstractGamePhase(int counter) {
        this.counter = counter;
    }

    public void startPhase() {
        scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gameapi.getGameapi(), () -> {}, 20L, 20L);
    };
    public void endPhase(){
        Bukkit.getScheduler().cancelTask(scheduler);
    };
}
