package net.thedudemc.dudeconfig;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.ConfigRegistry;
import net.thedudemc.dudeconfig.examples.AnotherConfig;
import net.thedudemc.dudeconfig.examples.TestConfig;

public class ConfigTest {

    private static boolean running = true;

    public static void main(String[] args) {

        ConfigRegistry.register(new TestConfig().read());
        ConfigRegistry.register(new AnotherConfig().read());

        try {
            Config someConfig = ConfigRegistry.getConfig("someConfig"); // this will throw an exception
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Config config = ConfigRegistry.getConfig("testConfig");

        long someLong = config.getLong("someLong");

        System.out.println("testConfig.someLong: " + someLong);

        ConfigRegistry.getConfig("anotherConfig").printOptions();
    }

}
