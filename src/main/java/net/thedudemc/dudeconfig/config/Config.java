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

    protected abstract String getName();

    protected abstract void reset();

    protected abstract Config getDefault();

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
