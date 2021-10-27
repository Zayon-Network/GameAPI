package de.nehlen.gameapi.TeamAPI;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamDestroyEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Team team;

    public TeamDestroyEvent(Team team) {
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
