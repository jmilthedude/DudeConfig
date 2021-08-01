package net.thedudemc.dudeconfig;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.ConfigRegistry;
import net.thedudemc.dudeconfig.examples.AnotherConfig;
import net.thedudemc.dudeconfig.examples.TestConfig;

public class ConfigTest {

    public static void main(String[] args) {

        // create some registries. this will create the folder (./config/ if unspecified)
        ConfigRegistry configRegistry = new ConfigRegistry("./test/SomeConfigs");
        ConfigRegistry anotherRegistry = new ConfigRegistry("./test/OtherConfigs");

        // register a config to one registry
        configRegistry.register(new TestConfig());

        // register another config to a different registry
        anotherRegistry.register(new AnotherConfig());

        // this will throw an exception because we have not registered "someConfig"
        try {
            Config someConfig = configRegistry.getConfig("someConfig");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        // Test some things on a config from the registry.
        TestConfig config = (TestConfig) configRegistry.getConfig("testConfig");

        System.out.println(config.getSomeObject("Thing1"));
        System.out.println(config.getSomeObject("Thing2"));

        config.markDirty();
        config.save();


    }

}
