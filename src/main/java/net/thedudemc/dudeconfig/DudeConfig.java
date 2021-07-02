package net.thedudemc.dudeconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.exception.InvalidOptionException;
import net.thedudemc.dudeconfig.option.Option;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

public abstract class DudeConfig {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String root = "config/";
    protected String extension = ".json";
    private boolean isDirty;

    @Expose protected final HashMap<String, Option> options = new HashMap<>();

    protected abstract void reset();

    public abstract String getName();

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void generateConfig() {
        this.reset();

        try {
            this.writeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getConfigFile() {
        return new File(this.root + this.getName() + this.extension);
    }


    public DudeConfig readConfig() {
        try {
            return GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generateConfig();
        }

        return this;
    }


    public void writeConfig() throws IOException {
        File dir = new File(this.root);
        if (!dir.exists() && !dir.mkdirs()) return;
        if (!this.getConfigFile().exists() && !this.getConfigFile().createNewFile()) return;
        FileWriter writer = new FileWriter(this.getConfigFile());
        GSON.toJson(this, writer);
        writer.flush();
        writer.close();
    }

    public void save() {
        if (this.isDirty) {
            try {
                this.writeConfig();
                this.isDirty = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* --------------------------------------------------------------------- */

    public String getString(String name) {
        Option<?> option = options.get(name);
        if (option != null && option.getValue() instanceof String) {
            return (String) option.getValue();
        }
        throw new InvalidOptionException(name, String.class.getSimpleName());
    }

    public int getInt(String name) {
        Option<?> option = options.get(name);
        if (option != null && option.getValue() instanceof Double && (double) option.getValue() == Math.floor((double) option.getValue())) {
            return ((Double) option.getValue()).intValue();
        }
        throw new InvalidOptionException(name, Integer.class.getSimpleName());
    }

    public double getDouble(String name) {
        Option<?> option = options.get(name);
        if (option != null && option.getValue() instanceof Double) {
            return (double) option.getValue();
        }
        throw new InvalidOptionException(name, Double.class.getSimpleName());
    }
}
