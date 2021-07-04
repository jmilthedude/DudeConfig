package net.thedudemc.dudeconfig;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.ConfigRegistry;
import net.thedudemc.dudeconfig.examples.AnotherConfig;
import net.thedudemc.dudeconfig.examples.TestConfig;

public class ConfigTest {

    public static void main(String[] args) {
        ConfigRegistry configRegistry = new ConfigRegistry("./test/MyAppConfigs");
        ConfigRegistry anotherRegistry = new ConfigRegistry("./test/OtherConfigs");

        configRegistry.register(new TestConfig());
        configRegistry.register(new AnotherConfig());

        anotherRegistry.register(new AnotherConfig());
        try {
            Config someConfig = configRegistry.getConfig("someConfig"); // this will throw an exception
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Config config = configRegistry.getConfig("testConfig");

        long someLong = config.getLong("someLong");

        System.out.println("testConfig.someLong: " + someLong);

        configRegistry.getConfig("anotherConfig").printOptions();
    }

}
