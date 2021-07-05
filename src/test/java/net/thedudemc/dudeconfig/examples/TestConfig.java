package net.thedudemc.dudeconfig.examples;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;

public class TestConfig extends Config {

    @Override
    public String getName() {
        return "testConfig";
    }

    @Override
    public OptionMap getDefaults() {
        OptionMap options = OptionMap.create();
        options.put("someInt", Option.of(12).withComment("This is an integer."));
        options.put("someLong", Option.of(54323L).withComment("This is a long."));
        options.put("someString", Option.of("This is some String").withComment("This is a String."));
        options.put("someBoolean", Option.of(false).withComment("This is a boolean."));
        options.put("someRangedOption", Option.of(.1f).withRange(0f, 1f).withComment("This is a test range."));
        return options;
    }
}
