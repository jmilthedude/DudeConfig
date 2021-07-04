package net.thedudemc.dudeconfig.examples;

import net.thedudemc.dudeconfig.config.Config;

public class TestConfig extends Config {

    @Override
    public String getName() {
        return "testConfig";
    }

    @Override
    protected void initialize() {
        putInt("someInt", 12);
        putLong("someLong", 54323L);
        putString("someString", "This is some String");
        putBoolean("someBoolean", false);
    }

}
