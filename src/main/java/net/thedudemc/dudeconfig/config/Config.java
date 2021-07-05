package net.thedudemc.dudeconfig.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;
import net.thedudemc.dudeconfig.exception.InvalidOptionException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Config {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String extension = ".json";
    private boolean isDirty;

    @Expose private OptionMap options = OptionMap.create();

    public abstract String getName();

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void generate(File rootDir) {
        this.options = getDefaults();

        try {
            this.writeToFile(rootDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getConfigFile(File rootDir) {
        return new File(rootDir, this.getName() + this.extension);
    }

    public Config read(File rootDir) {
        try {
            Config config = GSON.fromJson(new FileReader(this.getConfigFile(rootDir)), this.getClass());

            HashMap<String, Option<?>> defaults = getDefaults();
            if (defaults != null) {
                for (Map.Entry<String, Option<?>> entry : defaults.entrySet()) {
                    if (!config.options.containsKey(entry.getKey())) {
                        config.options.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            config.options.forEach((k, v) -> v.validateRange());

            config.markDirty();
            config.save(rootDir);

            return config;
        } catch (FileNotFoundException e) {
            this.generate(rootDir);
        }

        return this;
    }


    private void writeToFile(File rootDir) throws IOException {
        if (!rootDir.exists() && !rootDir.mkdirs()) return;
        if (!this.getConfigFile(rootDir).exists() && !this.getConfigFile(rootDir).createNewFile()) return;
        FileWriter writer = new FileWriter(this.getConfigFile(rootDir));
        GSON.toJson(this, writer);
        writer.flush();
        writer.close();
    }

    public void save(File rootDir) {
        if (this.isDirty) {
            try {
                this.writeToFile(rootDir);
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

    public void putString(String name, String value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putInt(String name, int value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putLong(String name, long value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putDouble(String name, double value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putFloat(String name, float value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putBoolean(String name, boolean value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putObject(String name, Object value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putStringList(String name, List<String> value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putIntList(String name, List<Integer> value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putLongList(String name, List<Long> value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putDoubleList(String name, List<Double> value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putFloatList(String name, List<Float> value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putBooleanList(String name, List<Boolean> value) {
        options.put(name, Option.of(value));
        this.markDirty();
    }

    public void putObjectList(String name, List<?> value) {
        options.put(name, Option.of(value));
        this.markDirty();
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
        if (option.getValue() instanceof Double || option.getValue() instanceof Float) {
            return (double) option.getValue();
        }
        throw new InvalidOptionException(Double.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }

    public double getFloat(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof Double || option.getValue() instanceof Float) {
            if ((double) option.getValue() > Float.MAX_VALUE)
                throw new InvalidOptionException(Float.class.getSimpleName(), Double.class.getSimpleName());
            return (float) option.getValue();
        }
        throw new InvalidOptionException(Double.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }
}
