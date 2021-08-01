package net.thedudemc.dudeconfig.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Extend from this class to create your own config files. Each field shall be a config option and must be annotated
 * with {@link Expose} if you wish to write the value to the config file.
 */
public abstract class Config {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    private boolean isDirty;
    private File rootDir;

    /**
     * This is the name of the file for the config.
     *
     * @return The name of the config file.
     */
    protected abstract String getName();

    /**
     * Set the default values for the fields in your Config class.
     */
    protected abstract void reset();

    /**
     * This will return the defaults for comparing with stored JSON objects
     * which have have missing options. If a field is found to be null, it will
     * check this default instance (using {@link Config#reset()}) and add the
     * default values.
     *
     * @return default instance of this config.
     */
    private Config getDefault() {
        Class<?> clazz = this.getClass();
        try {
            Config config = (Config) clazz.getDeclaredConstructor().newInstance();
            config.reset();
            return config;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This must be executed when a change is made to the config to ensure that
     * new value is written to the file.
     */
    public void markDirty() {
        this.isDirty = true;
    }

    /**
     * Creates the config file if one does not exist.
     */
    private void generate() {
        this.reset();

        try {
            this.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convenience method used to create the path to this specific config file.
     *
     * @return the config file.
     */
    private File getConfigFile() {
        return new File(this.rootDir, this.getName() + ".json");
    }

    /**
     * Set the root directory of this config file based on the registry root dir value.
     *
     * @param rootDir directory which to store this config file.
     */
    protected void setRootDirectory(File rootDir) {
        this.rootDir = rootDir;
    }

    /**
     * This is the entry point of a config file. Use this to instantiate your own config object.
     * <p>
     * Reads an existing json file and creates the config object. If no json file exists, one is then created
     * based on the default values in {@link Config#reset()}.
     *
     * @return a new config file.
     */
    public Config read() {
        try {
            Config config = GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());

            Config defaultConfig = this.getDefault();

            setMissingDefaults(config, defaultConfig);

            config.setRootDirectory(this.rootDir);

            config.markDirty();
            config.save();

            return config;
        } catch (
                FileNotFoundException e) {
            this.generate();
        }

        return this;
    }

    /**
     * Uses reflection to replace missing values if they are null.
     *
     * @param config        the config read from file.
     * @param defaultConfig a default config generated via {@link Config#reset()}
     */
    private void setMissingDefaults(Config config, Config defaultConfig) {
        Arrays.stream(config.getClass().getDeclaredFields()).forEach(field -> {
            try {
                if (field.isAnnotationPresent(Expose.class)) {
                    boolean access = field.canAccess(config);
                    field.setAccessible(true);
                    Object stored = field.get(config);
                    Object defaultObj = field.get(defaultConfig);
                    if (stored == null) {
                        field.set(config, defaultObj);
                    }
                    field.setAccessible(access);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }


    private void writeToFile() throws IOException {
        if (!this.rootDir.exists() && !this.rootDir.mkdirs()) return;
        if (!this.getConfigFile().exists() && !this.getConfigFile().createNewFile()) return;
        FileWriter writer = new FileWriter(this.getConfigFile());
        GSON.toJson(this, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Called to save this config file when {@link Config#markDirty()} has been set.
     */
    public void save() {
        if (this.isDirty) {
            try {
                this.writeToFile();
                this.isDirty = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
