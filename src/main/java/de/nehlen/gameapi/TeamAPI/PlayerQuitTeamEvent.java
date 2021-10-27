package de.nehlen.gameapi.TeamAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerQuitTeamEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Player player;
    private Team team;

    public PlayerQuitTeamEvent(Player player, Team team) {
        this.player = player;
        this.team = team;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public Player getPlayer()
    {
        return this.player;
    }
    public boolean isCancelled()
    {
        return this.cancelled;
    }
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public Team getTeam() {
        return this.team;
    }
}
