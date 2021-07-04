package net.thedudemc.dudeconfig.config;

import java.util.HashMap;

public class ConfigRegistry {

    private static final HashMap<String, Config> REGISTRY = new HashMap<>();


    public static void register(Config config) {
        if (REGISTRY.containsKey(config.getName()))
            throw new IllegalArgumentException("Config with name \"" + config.getName() + "\" already exists.");

        REGISTRY.put(config.getName(), config);
    }

    public static Config getConfig(String name) {
        if (!REGISTRY.containsKey(name))
            throw new IllegalArgumentException("Config with name \"" + name + "\" does not exist.");

        return REGISTRY.get(name);
    }

    public static void saveAll() {
        for (Config config : REGISTRY.values()) {
            config.save();
        }
    }

}
