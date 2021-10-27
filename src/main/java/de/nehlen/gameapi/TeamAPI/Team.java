package de.nehlen.gameapi.TeamAPI;

import de.nehlen.gameapi.Gameapi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/*

    TeamApi for the spigot-api, especially for gamemodes like skywars or bedwars where teams are needed

    @author Niklas Ehlen
    @version 1.0
*/
public class Team {

    private ArrayList<Player> registeredPlayers;
    private Integer maxTeamSize;
    private ChatColor teamColor;
    private Integer uuid;
    private HashMap<String, Object> memory;
    String teamName;
    private TeamAPI teamAPI;

    public Team() {
        this.registeredPlayers = new ArrayList<Player>();
        this.maxTeamSize = 2;
        this.teamColor = ChatColor.WHITE;
        this.memory = new HashMap<String, Object>();
        this.teamAPI = Gameapi.getGameapi().getTeamAPI();
    }

    /**
     * Adds a player from this team.
     *
     * @param player
     */
    public void addPlayer(Player player) {

        PlayerJoinTeamEvent playerJoinTeamEvent = new PlayerJoinTeamEvent(player, this);
        PlayerQuitTeamEvent playerQuitTeamEvent = new PlayerQuitTeamEvent(player, this);

        if (this.teamAPI.contains(this)) {
            if (!(this.registeredPlayers.size() >= this.maxTeamSize)) {
                for (int i = 0; i != this.teamAPI.getRegisteredTeams().size(); i++) {
                    if (this.teamAPI.getRegisteredTeams().get(i).contains(player)) {

                        Bukkit.getScheduler().runTask(Gameapi.getGameapi(), () ->Bukkit.getPluginManager().callEvent(playerQuitTeamEvent));
                        if (!playerQuitTeamEvent.isCancelled())
                            this.teamAPI.getRegisteredTeams().get(i).removePlayer(player);
                    }
                }
                Bukkit.getScheduler().runTask(Gameapi.getGameapi(), () ->Bukkit.getPluginManager().callEvent(playerJoinTeamEvent));
                if (!playerJoinTeamEvent.isCancelled()) {
                    this.registeredPlayers.add(player);
                    player.setDisplayName(this.getTeamColor() + player.getCustomName());
                }
            }
        }
    }

    /**
     * Removes a player from this team.
     *
     * @param player
     */
    public void removePlayer(Player player) {
        PlayerQuitTeamEvent playerQuitTeamEvent = new PlayerQuitTeamEvent(player, this);

        if (this.teamAPI.contains(this))
            if (this.registeredPlayers.contains(player)) {
                Bukkit.getScheduler().runTask(Gameapi.getGameapi(), () ->Bukkit.getPluginManager().callEvent(playerQuitTeamEvent));
                if (!playerQuitTeamEvent.isCancelled())
                    this.registeredPlayers.remove(player);
            }
    }

    public void addToMemory(String key, Object object) {
        this.memory.put(key, object);
    }
    public void removeFromMemory(String key) {
        this.memory.remove(key);
    }
    public void replaceFromMemory(String key, Object object) {
        this.memory.replace(key, object);
    }

    /**
     * Checks if a player is in this team.
     *
     * @param player
     * @return boolean
     */
    public boolean contains(Object player) {
        if (this.registeredPlayers.contains(player))
            return true;
        return false;
    }

    /**
     * Checks if a team if full of players.
     *
     * @return boolean
     */
    public boolean isFull() {
        if (this.registeredPlayers.size() >= this.maxTeamSize)
            return true;
        return false;
    }

    /**
     * Returns size of Team
     * @return integer
     */
    public Integer size() {
        return this.registeredPlayers.size();
    }

    public ArrayList<Player> getRegisteredPlayers() {
        return this.registeredPlayers;
    }

    public Integer getMaxTeamSize() {
        return this.maxTeamSize;
    }

    public ChatColor getTeamColor() {
        return this.teamColor;
    }

    public Integer getUuid() {
        return this.uuid;
    }

    public HashMap<String, Object> getMemory() {
        return this.memory;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setMaxTeamSize(Integer maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public void setTeamColor(ChatColor teamColor) {
        this.teamColor = teamColor;
    }

    public void setMemory(HashMap<String, Object> memory) {
        this.memory = memory;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
