package de.nehlen.gameapi.TeamAPI;

import de.nehlen.gameapi.Gameapi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamAPI {

    private final Gameapi gameapi;
    private ArrayList<Team> registeredTeams = new ArrayList<Team>();

    public TeamAPI(final Gameapi gameapi) {
        this.gameapi = gameapi;
    }

    /**
     * Checks if team already exists.
     *
     * @param team
     * @return boolean
     */
    public boolean contains(Team team) {
        if (registeredTeams.contains(team))
            return true;
        return false;
    }

    /**
     * Adds the player to a team that is not yet full
     *
     * @param player
     */
    public void addPlayerRandom(Player player) {
        Random r = new Random();
        int i = r.nextInt(getRegisteredTeams().size());
        for (Team team : getRegisteredTeams()) {
            if (team.contains(player))
                team.removePlayer(player);
        }
        if (!getRegisteredTeams().get(i).isFull()) {
            getRegisteredTeams().get(i).addPlayer(player);
        } else {
            addPlayerRandom(player);
        }
    }


    /**
     * Adds Player to Team with the lowest Players
     *
     * @param player
     */
    public Team addToLowestTeam(Player player) {
        Team lowest = this.getRegisteredTeams().get(0);
        if (this.getRegisteredTeams().size() > 1) {
            for (int i = 1; i != this.getRegisteredTeams().size(); i++) {
                if (this.getRegisteredTeams().get(i).getRegisteredPlayers().size() < lowest
                        .getRegisteredPlayers().size()) {
                    lowest = this.getRegisteredTeams().get(i);
                }
            }
            lowest.addPlayer(player);
            return lowest;
        }
        return null;
    }


    /**
     * Register a new team
     *
     * @param team
     */
    public void addTeam(Team team) {
        TeamCreateEvent teamCreateEvent = new TeamCreateEvent(team);

        if (!this.registeredTeams.contains(team)) {

            Bukkit.getScheduler().runTask(Gameapi.getGameapi(), () -> Bukkit.getPluginManager().callEvent(teamCreateEvent));
            if (!teamCreateEvent.isCancelled()) {
                this.registeredTeams.add(team);
            }
        }
    }

    /**
     * Remove a registered team
     *
     * @param team
     */
    public void removeTeam(Team team) {
        TeamDestroyEvent teamDestroyEventEvent = new TeamDestroyEvent(team);
        Bukkit.getScheduler().runTask(Gameapi.getGameapi(), () -> Bukkit.getPluginManager().callEvent(teamDestroyEventEvent));
        if (!teamDestroyEventEvent.isCancelled())
            this.registeredTeams.remove(team);
    }

    /**
     * Remove a registered team with its index
     *
     * @param index
     */
    public void removeTeam(int index) {
        TeamDestroyEvent teamDestroyEventEvent = new TeamDestroyEvent(registeredTeams.get(index));
        Bukkit.getScheduler().runTask(Gameapi.getGameapi(), () -> Bukkit.getPluginManager().callEvent(teamDestroyEventEvent));
        if (!teamDestroyEventEvent.isCancelled())
            this.registeredTeams.remove(index);
    }

    /**
     * Remove all empty Teams
     */
    public void removeEmptyTeams() {
        List<Team> toRemove = new ArrayList();
        this.getRegisteredTeams().forEach(team -> {
            if (team.getRegisteredPlayers().isEmpty()) {
                Bukkit.getConsoleSender().sendMessage("" + team.getRegisteredPlayers().size());
                TeamDestroyEvent teamDestroyEventEvent = new TeamDestroyEvent(team);
                Bukkit.getScheduler().runTask(Gameapi.getGameapi(), () -> Bukkit.getPluginManager().callEvent(teamDestroyEventEvent));
                if(!teamDestroyEventEvent.isCancelled()) {
                    toRemove.add(team);
                }
            }
        });
        this.registeredTeams.removeAll(toRemove);
    }


    public ArrayList<Team> getRegisteredTeams() {
        return this.registeredTeams;
    }
}
