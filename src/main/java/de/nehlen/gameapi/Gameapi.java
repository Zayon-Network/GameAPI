package de.nehlen.gameapi;

import de.nehlen.gameapi.YamlConfiguration.SpigotConfigurationWrapper;
import de.nehlen.gameapi.YamlConfiguration.YamlConfig;
import de.nehlen.gameapi.PointsAPI.PointsAPI;
import de.nehlen.gameapi.TeamAPI.TeamAPI;
import de.nehlen.gameapi.listener.PlayerJoinListener;
import de.nehlen.gameapi.util.DatabaseLib;
import de.nehlen.gameapi.util.ServerID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Gameapi extends JavaPlugin {


    @Getter private static Gameapi gameapi;
    @Getter private ServerID serverID;
    @Getter private SpigotConfigurationWrapper databaseConfig;

    //LISTENER
    @Getter private PlayerJoinListener playerJoinListener;

    //APIS
    @Getter private TeamAPI teamAPI;
    @Getter private DatabaseLib databaseLib;
    @Getter private PointsAPI pointsAPI;

    @Override
    public void onEnable() {
        gameapi = this;
        serverID = new ServerID(ServerID.generateRandomServerID());

        this.databaseConfig = new YamlConfig();
        this.databaseConfig.load(new File(getDataFolder(), "database_config.yml"));

        this.teamAPI = new TeamAPI(this);
        this.databaseLib = new DatabaseLib(this);
        this.pointsAPI = new PointsAPI(this);

        this.playerJoinListener = new PlayerJoinListener(this);

        //CREATE DATABASE TABLES
        pointsAPI.createTable();

        //REGISTER LISTENER
        Bukkit.getPluginManager().registerEvents(playerJoinListener, this);
    }

    @Override
    public void onDisable() {
        this.databaseLib.close();
    }
}
