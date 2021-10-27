package de.nehlen.gameapi.PointsAPI;

import de.nehlen.gameapi.Gameapi;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class PointsAPI {

    private final Gameapi gameapi;

    public PointsAPI(final Gameapi gameapi) {
        this.gameapi = gameapi;
    }

    public void createTable() {
        StringBuilder table = new StringBuilder();
        table.append("id INT(11) NOT NULL AUTO_INCREMENT, ");
        table.append("`uuid` VARCHAR(64) NOT NULL UNIQUE, ");
        table.append("`points` INT(11) NOT NULL, ");
        table.append("PRIMARY KEY (`id`)");
        gameapi.getDatabaseLib().executeUpdateAsync("CREATE TABLE IF NOT EXISTS player_points (" + table.toString() + ")", resultSet -> {});
    }

    public CompletableFuture<Boolean> userExists(final Player player) {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        gameapi.getDatabaseLib().executeQueryAsync("SELECT id FROM player_points WHERE uuid = ?", resultSet -> {
            try {
                completableFuture.complete(resultSet.next());
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        }, player.getUniqueId().toString());
        return completableFuture;
    }

    public void createUser(final Player player) {
        userExists(player).whenCompleteAsync((exist, throwable) -> {
            if(throwable == null && !exist) {
                gameapi.getDatabaseLib().executeUpdateAsync("INSERT INTO player_points (uuid, points) VALUES (?, ?)", resultSet -> {
                }, player.getUniqueId().toString(), 0);
            }
        });
    }

    public int getPoints(final Player player) {
        return (Integer) gameapi.getDatabaseLib().get("SELECT points FROM player_points WHERE uuid = ?", player.getUniqueId().toString(), "points");
    }

    public int getPoints(final OfflinePlayer player) {
        return (Integer) gameapi.getDatabaseLib().get("SELECT points FROM player_points WHERE uuid = ?", player.getUniqueId().toString(), "points");
    }

    public void updatePoints(final Player player, final UpdateType updateType, int points) {
        int newPoints = 0;
        PointsChangeEvent pointsChangeEvent = new PointsChangeEvent(player, points, updateType);
        Bukkit.getScheduler().runTask(this.gameapi, () ->Bukkit.getPluginManager().callEvent(pointsChangeEvent));

        if(!pointsChangeEvent.isCancelled()) {
            if (updateType == UpdateType.ADD) newPoints = getPoints(player) + points;
            else if (updateType == UpdateType.REMOVE) newPoints = getPoints(player) - points;
            else if (updateType == UpdateType.SET) newPoints = points;
            gameapi.getDatabaseLib().executeUpdateAsync("UPDATE player_points SET points = ? WHERE uuid = ?", resultSet -> {
            }, newPoints, player.getUniqueId().toString());
        }
    }

    public void updatePoints(final OfflinePlayer player, final UpdateType updateType, int points) {
        PointsChangeEvent pointsChangeEvent = new PointsChangeEvent(player, points, updateType);
        Bukkit.getScheduler().runTask(this.gameapi, () ->Bukkit.getPluginManager().callEvent(pointsChangeEvent));

        if(!pointsChangeEvent.isCancelled()) {
            int newPoints = 0;
            if (updateType == UpdateType.ADD) newPoints = getPoints(player) + points;
            else if (updateType == UpdateType.REMOVE) newPoints = getPoints(player) - points;
            else if (updateType == UpdateType.SET) newPoints = points;
            gameapi.getDatabaseLib().executeUpdateAsync("UPDATE player_points SET points = ? WHERE uuid = ?", resultSet -> {
            }, newPoints, player.getUniqueId().toString());
        }
    }

    public enum UpdateType {
        ADD,
        REMOVE,
        SET
    }

}
