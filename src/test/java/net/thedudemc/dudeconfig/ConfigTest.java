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
            e.printStackTrace();
        }


        // Test some things on a config from the registry.
        Config config = configRegistry.getConfig("testConfig");

        config.printOptions();

        System.out.println("testConfig.someLong (before): " + config.getLong("someLong"));

        config.putLong("someLong", 666L);
        config.putString("tryThis", "This is a string added later after instantiation.");
        config.save(configRegistry.getRootDir());

        System.out.println("testConfig.someLong (after): " + config.getLong("someLong"));
    }

}
