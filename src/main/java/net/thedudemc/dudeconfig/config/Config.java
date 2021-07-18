package net.thedudemc.dudeconfig.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;
import net.thedudemc.dudeconfig.exception.InvalidOptionException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Config {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String extension = ".json";
    private boolean isDirty;
    private File rootDir;

    @Expose
    private OptionMap options = OptionMap.create();

    public abstract String getName();

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void generate() {
        this.options = getDefaults();

        try {
            this.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getConfigFile() {
        return new File(this.rootDir, this.getName() + this.extension);
    }

    public void setRootDirectory(File rootDir) {
        this.rootDir = rootDir;
    }

    public Config read() {
        try {
            Config config = GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());

            config.setRootDirectory(this.rootDir);

            HashMap<String, Option> defaults = getDefaults();
            if (defaults != null) {
                for (Map.Entry<String, Option> entry : defaults.entrySet()) {
                    if (!config.options.containsKey(entry.getKey())) {
                        config.options.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            config.options.forEach((k, v) -> {
                v.validateRange(); // if option has range, validate it.

                // here we will convert a gson map to a hashmap
                if (v.getValue() instanceof LinkedTreeMap) {
                    LinkedTreeMap<?, ?> map = (LinkedTreeMap<?, ?>) v.getValue();
                    HashMap<?, ?> temp = new HashMap<>(map);
                    v.setRawValue(temp);
                }
                if (v.getValue() instanceof Double) {
                    double value = v.getDoubleValue();
                    if (Math.floor(value) == (long) value) {
                        v.setRawValue((long) value);
                    }
                }
            });

            config.markDirty();
            config.save();

            return config;
        } catch (FileNotFoundException e) {
            this.generate();
        }

        return this;
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

    public void printOptions() {
        options.forEach((k, v) -> {
            System.out.println(k + ": " + v.getValue());
        });
    }

    /* --------------------------------------------------------------------- */

    public abstract OptionMap getDefaults();

    public Option getOption(String name) {
        if (options.get(name) == null) throw new InvalidOptionException(name);

        return options.get(name);
    }
}
