package net.thedudemc.dudeconfig.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.util.Arrays;

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
    protected abstract Config getDefault();

    /**
     * This must be executed when a change is made to the config to ensure that
     * new value is written to the file.
     */
    public void markDirty() {
        this.isDirty = true;
    }

    private void generate() {
        this.reset();

        try {
            this.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getConfigFile() {
        return new File(this.rootDir, this.getName() + ".json");
    }

    protected void setRootDirectory(File rootDir) {
        this.rootDir = rootDir;
    }

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
