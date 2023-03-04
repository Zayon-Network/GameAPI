package de.nehlen.gameapi.PhaseApi;

import de.nehlen.gameapi.Gameapi;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

public class GamePhase {
    @Getter @Setter
    public int counter = 0;
    public int scheduler = 0;

    public GamePhase(int counter) {
        this.counter=counter;
    }

    public void startPhase() {
        scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gameapi.getGameapi(), new Runnable() {
            @Override
            public void run() {
                //PUT CONTENT HERE
            }
        }, 20L, 20L);
    };
    public void endPhase(){
        Bukkit.getScheduler().cancelTask(scheduler);
    };
}
