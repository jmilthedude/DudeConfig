package net.thedudemc.dudeconfig.examples;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.Option;

import java.util.HashMap;

public class TestConfig extends Config {

    @Override
    public String getName() {
        return "testConfig";
    }

    @Override
    public HashMap<String, Option<?>> getDefaults() {
        HashMap<String, Option<?>> options = new HashMap<>();
        options.put("someInt", Option.of(12).withComment("This is an integer."));
        options.put("someLong", Option.of(54323L).withComment("This is a long."));
        options.put("someString", Option.of("This is some String").withComment("This is a String."));
        options.put("someBoolean", Option.of(false).withComment("This is a boolean."));
        return options;
    }
}
