package de.nehlen.gameapi.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.nehlen.gameapi.Gameapi;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class DatabaseLib implements Closeable {
    private final Gameapi gameapi;

    private final HikariDataSource hikariDataSource;

    public DatabaseLib(Gameapi gameapi) {
        this.gameapi = gameapi;
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(gameapi.getDatabaseConfig().getOrSetDefault("config.database.url", "jdbc:mysql://localhost:3306/database"));
        hikariConfig.setUsername(gameapi.getDatabaseConfig().getOrSetDefault("config.database.username", "username"));
        hikariConfig.setPassword(gameapi.getDatabaseConfig().getOrSetDefault("config.database.password", "password"));
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        gameapi.getDatabaseConfig().save();
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void execute(String query, Object... args) {
        try(final Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, args);
                preparedStatement.execute();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void executeUpdate(String query, Consumer<Integer> callback, Object... args) {
        try(final Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, args);
                callback.accept(preparedStatement.executeUpdate());
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void executeQuery(String query, Consumer<ResultSet> callback, Object... args) {
        try(final Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, args);
                callback.accept(preparedStatement.executeQuery());
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void executeAsync(final String query, final Object... args) {
        new Thread(() -> {
            try(final Connection connection = hikariDataSource.getConnection()) {
                try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    addArguments(preparedStatement, args);
                    preparedStatement.execute();
                    hikariDataSource.getConnection().close();
                } catch(SQLException exception) {
                    exception.printStackTrace();
                }
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public void executeUpdateAsync(final String query, final Consumer<Integer> callback, final Object... args) {
        new Thread(() -> {
            try(final Connection connection = hikariDataSource.getConnection()) {
                try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    addArguments(preparedStatement, args);
                    callback.accept(preparedStatement.executeUpdate());
                    hikariDataSource.getConnection().close();
                }
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public void executeQueryAsync(final String query, final org.bukkit.util.Consumer<ResultSet> callback, final Object... args) {
        new Thread(() -> {
            try(final Connection connection = hikariDataSource.getConnection()) {
                try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    addArguments(preparedStatement, args);
                    callback.accept(preparedStatement.executeQuery());
                    hikariDataSource.getConnection().close();
                } catch(SQLException exception) {
                    exception.printStackTrace();
                }
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public Object get(String query, String arg, String selection) {
        try(final Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, arg);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()) return resultSet.getObject(selection);
                } catch(SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void addArguments(PreparedStatement preparedStatement, Object... args) {
        try {
            int position = 1;
            for(final Object arg : args) {
                preparedStatement.setObject(position, arg);
                position++;
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            hikariDataSource.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public HikariDataSource getHikariDataSource() {
        return this.hikariDataSource;
    }
}
