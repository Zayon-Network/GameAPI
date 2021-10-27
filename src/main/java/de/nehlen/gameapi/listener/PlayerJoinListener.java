package de.nehlen.gameapi.listener;

import de.nehlen.gameapi.Gameapi;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Gameapi gameapi;

    public PlayerJoinListener(final Gameapi gameapi) {
        this.gameapi = gameapi;
    }

    @EventHandler
    public void handleJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        gameapi.getPointsAPI().createUser(player);
    }

}
