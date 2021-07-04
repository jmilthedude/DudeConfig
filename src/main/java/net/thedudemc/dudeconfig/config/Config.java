package net.thedudemc.dudeconfig.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.exception.InvalidOptionException;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public abstract class Config {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String root = "config/";
    protected String extension = ".json";
    private boolean isDirty;

    @Expose private final HashMap<String, Option<?>> options = new HashMap<>();

    protected abstract void initialize();

    public abstract String getName();

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void generate() {
        this.initialize();

        try {
            this.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getConfigFile() {
        return new File(this.root + this.getName() + this.extension);
    }

    public Config read() {
        try {
            return GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generate();
        }

        return this;
    }


    private void writeToFile() throws IOException {
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

    public void putString(String name, String value) {
        options.put(name, Option.of(value));
    }

    public void putInt(String name, int value) {
        options.put(name, Option.of(value));
    }

    public void putLong(String name, long value) {
        options.put(name, Option.of(value));
    }

    public void putDouble(String name, double value) {
        options.put(name, Option.of(value));
    }

    public void putFloat(String name, float value) {
        options.put(name, Option.of(value));
    }

    public void putBoolean(String name, boolean value) {
        options.put(name, Option.of(value));
    }

    public void putObject(String name, Object value) {
        options.put(name, Option.of(value));
    }

    public void putStringList(String name, List<String> value) {
        options.put(name, Option.of(value));
    }

    public void putIntList(String name, List<Integer> value) {
        options.put(name, Option.of(value));
    }

    public void putLongList(String name, List<Long> value) {
        options.put(name, Option.of(value));
    }

    public void putDoubleList(String name, List<Double> value) {
        options.put(name, Option.of(value));
    }

    public void putFloatList(String name, List<Float> value) {
        options.put(name, Option.of(value));
    }

    public void putBooleanList(String name, List<Boolean> value) {
        options.put(name, Option.of(value));
    }

    public void putObjectList(String name, List<?> value) {
        options.put(name, Option.of(value));
    }

    private Option<?> getOption(String name) {
        if (options.get(name) == null) throw new InvalidOptionException(name);

        return options.get(name);
    }

    public String getString(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof String) {
            return (String) option.getValue();
        }
        throw new InvalidOptionException(String.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }

    public int getInt(String name) {
        Option<?> option = getOption(name);

        if (option.getValue() instanceof Integer) return (Integer) option.getValue();

        if (option.getValue() instanceof Double && (double) option.getValue() == Math.floor((double) option.getValue())) {
            return ((Double) option.getValue()).intValue();
        }
        throw new InvalidOptionException(Integer.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }

    public long getLong(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof Long) return (Long) option.getValue();

        if (option.getValue() instanceof Double && (double) option.getValue() == Math.floor((double) option.getValue())) {
            return ((Double) option.getValue()).longValue();
        }

        throw new InvalidOptionException(Long.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }

    public double getDouble(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof Double) {
            return (double) option.getValue();
        }
        throw new InvalidOptionException(Double.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }
}
