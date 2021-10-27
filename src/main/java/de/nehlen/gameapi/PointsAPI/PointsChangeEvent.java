package de.nehlen.gameapi.PointsAPI;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PointsChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private OfflinePlayer player;
    private int points;
    private PointsAPI.UpdateType type;

    public PointsChangeEvent(OfflinePlayer player, int points, PointsAPI.UpdateType updateType) {
        this.player = player;
        this.points = points;
        this.type = updateType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public OfflinePlayer getPlayer()
    {
        return this.player;
    }

    public int getPoints()
    {
        return this.points;
    }

    public PointsAPI.UpdateType getType()
    {
        return this.type;
    }

    public boolean isCancelled()
    {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

}
