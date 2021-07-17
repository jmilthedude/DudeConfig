package net.thedudemc.dudeconfig.config;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * When using this library, a {@link ConfigRegistry} is required.
 * First, instantiate one with a folder name or leave null to use the
 * default of ./config
 */
public class ConfigRegistry {

    private final String folder;
    private final HashMap<String, Config> REGISTRY = new HashMap<>();

    /**
     * Class constructor with no folder value. Will use the default.
     */
    public ConfigRegistry() {
        this(null);
    }

    /**
     * Class constructor with a folder value.
     * Used to designate a location to save the
     * config files registered to this registry.
     *
     * @param folderName The folder to use for this registry.
     */
    public ConfigRegistry(String folderName) {
        this.folder = folderName;
    }

    /**
     * Adds to this registry a {@link Config} class.
     * <p>
     * This will first check that there is not already
     * a config with the set {@link Config#getName() }
     *
     * @param config A {@link Config} object to register.
     * @throws IllegalArgumentException When a name with the specified config name already exists.
     */
    public void register(Config config) {
        if (REGISTRY.containsKey(config.getName())) {
            throw new IllegalArgumentException("Config with name \"" + config.getName() + "\" already exists.");
        }

        config.setRootDirectory(getRootDir());

        REGISTRY.put(config.getName(), config.read());
    }

    /**
     * Returns a collection of all {@link Config} objects registered
     * to this registry.
     *
     * @return A collection of {@link Config} objects.
     */
    public Collection<Config> getAll() {
        return this.REGISTRY.values();
    }

    /**
     * Get a {@link Config} with the specified name.
     *
     * @param name The name of the config file as set by {@link Config#getName()}
     * @return The {@link Config} file as specified by the name.
     * @throws IllegalArgumentException When a config with the specified name does not exist.
     */
    public Config getConfig(String name) {
        if (!REGISTRY.containsKey(name)) {
            throw new IllegalArgumentException("Config with name \"" + name + "\" does not exist.");
        }

        return REGISTRY.get(name);
    }

    /**
     * Save all config files within this registry.
     */
    public void saveAll() {
        for (Config config : REGISTRY.values()) {
            config.save();
        }
    }

    /**
     * Save a specific config file within this registry.
     *
     * @param name the name of the config file to save.
     */
    public void saveConfig(String name) {
        Config config = getConfig(name);
        config.save();
    }

    /**
     * Get the root directory of this registry. This is the folder specified or default.
     *
     * @return the root directory {@link File}
     */
    public File getRootDir() {
        return new File(Objects.requireNonNullElse(this.folder, "./config"));
    }
}
