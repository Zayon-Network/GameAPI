package de.nehlen.gameapi.YamlConfiguration;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlConfig implements SpigotConfigurationWrapper {

    private final YamlConfiguration config = new YamlConfiguration();
    private File configFile;

    @Override
    public SpigotConfigurationWrapper load(File file) {
        File directory = file.getParentFile();
        try {
            if (file.exists()) {
                config.load(file);
                configFile = file;
            } else {
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                file.createNewFile();
                this.load(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void save() {
        if (configFile != null) {
            try {
                getConfig().save(configFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reload() throws IOException {
        if (configFile != null) {
            try {
                config.load(configFile);
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }

    @Override
    public boolean contains(String path) {
        try {
            return config.contains(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(String path, Object value) {
        try {
            config.set(path, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object get(String path) {
        try {
            return config.get(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(String path) {
        try {
            return config.getString(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInt(String path) {
        try {
            return config.getInt(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble(String path) {
        try {
            return config.getDouble(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getBoolean(String path) {
        try {
            return config.getBoolean(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public YamlConfiguration getConfig() {
        return config;
    }

    @Override
    public Object getOrSetDefault(String path, Object defaultValue) {
        if (contains(path)) {
            return get(path);
        } else {
            set(path, defaultValue);
            return defaultValue;
        }
    }

}

