package net.thedudemc.dudeconfig.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;
import net.thedudemc.dudeconfig.exception.InvalidOptionException;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashMap;
import java.util.List;
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

            HashMap<String, Option<?>> defaults = getDefaults();
            if (defaults != null) {
                for (Map.Entry<String, Option<?>> entry : defaults.entrySet()) {
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
                    config.options.put(k, Option.of(temp));
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

    public void setOption(String name, @Nullable Option<?> option, Object newValue) {
        options.put(name, Option.of(newValue).withComment(option == null ? null : option.getComment()));
        this.markDirty();
    }

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

    public Option<?> getOption(String name) {
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

    public float getFloat(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof Float) return (float) option.getValue();
        if (option.getValue() instanceof Double) {
            if ((Double) option.getValue() > Float.MAX_VALUE)
                throw new InvalidOptionException(Float.class.getSimpleName(), Double.class.getSimpleName());
            return ((Double) option.getValue()).floatValue();
        }
        throw new InvalidOptionException(Double.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }

    public boolean getBoolean(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof Boolean) return (boolean) option.getValue();

        if (option.getValue() instanceof Number) {
            Number value = (Number) option.getValue();
            return value.intValue() == 1;
        } else if (option.getValue() instanceof String) {
            String value = (String) option.getValue();
            return "true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }

        return false;
    }

    public HashMap<?, ?> getMap(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof Map) {
            return (HashMap<?, ?>) option.getValue();
        }
        throw new InvalidOptionException(HashMap.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }

    public List<?> getList(String name) {
        Option<?> option = getOption(name);
        if (option.getValue() instanceof List) {
            return (List<?>) option.getValue();
        }
        throw new InvalidOptionException(List.class.getSimpleName(), option.getValue().getClass().getSimpleName());
    }

}
