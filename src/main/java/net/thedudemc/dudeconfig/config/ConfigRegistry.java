package net.thedudemc.dudeconfig.config;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class ConfigRegistry {

    private final String folder;
    private final HashMap<String, Config> REGISTRY = new HashMap<>();

    public ConfigRegistry() {
        this(null);
    }

    public ConfigRegistry(String folderName) {
        this.folder = folderName;
    }

    public void register(Config config) {
        if (REGISTRY.containsKey(config.getName())) {
            throw new IllegalArgumentException("Config with name \"" + config.getName() + "\" already exists.");
        }

        File rootDir = getRootDir();

        REGISTRY.put(config.getName(), config.read(rootDir));
    }

    public Config getConfig(String name) {
        if (!REGISTRY.containsKey(name)) {
            throw new IllegalArgumentException("Config with name \"" + name + "\" does not exist.");
        }

        return REGISTRY.get(name);
    }

    public void saveAll() {
        for (Config config : REGISTRY.values()) {
            config.save(getRootDir());
        }
    }

    public void saveConfig(String name) {
        Config config = getConfig(name);
        config.save(this.getRootDir());
    }

    public File getRootDir() {
        return new File(Objects.requireNonNullElse(this.folder, "./config"));
    }
}
