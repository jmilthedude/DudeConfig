import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public abstract class DudeConfig {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String root = "";
    protected String extension = ".json";

    private boolean isDirty;

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

    public abstract String getName();

    public DudeConfig readConfig(String folder) {
        this.root = folder;
        try {
            return GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generateConfig();
        }

        return this;
    }

    protected abstract void reset();

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
        if(this.isDirty) {
            try {
                this.writeConfig();
                this.isDirty = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
