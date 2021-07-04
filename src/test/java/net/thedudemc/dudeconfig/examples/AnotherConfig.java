package net.thedudemc.dudeconfig.examples;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.examples.object.SomeObject;

import java.util.Arrays;

public class AnotherConfig extends Config {
    @Override
    protected void initialize() {
        putInt("anotherInt", 12);
        putLong("anotherLong", Long.MAX_VALUE);
        putString("anotherString", "This is another String");
        putBoolean("anotherBoolean", false);
        putStringList("someStringList", Arrays.asList("a", "b", "c"));
        putObject("someObject", new SomeObject("objectName", 666, 0.1f));
    }

    @Override
    public String getName() {
        return "anotherConfig";
    }
}
