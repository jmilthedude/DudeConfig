package net.thedudemc.dudeconfig;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.ConfigRegistry;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.examples.AnotherConfig;
import net.thedudemc.dudeconfig.examples.TestConfig;
import net.thedudemc.dudeconfig.exception.InvalidOptionException;

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
        Config config = configRegistry.getConfig("testConfig");

        config.printOptions();

        Option someLong = config.getOption("someLong");

        System.out.println("testConfig.someLong (before): " + someLong);

        someLong.setValue(666L);

        System.out.println("testConfig.someLong (after): " + someLong);

        // test a ranged option
        System.out.println("testConfig.someRangedOption: " + config.getOption("someRangedOption").getFloatValue());

        // test a map option.
        System.out.println("someMap.something: " + config.getOption("someMap").getMapValue().get("something"));

        // test a list option
        System.out.println("someList.index(0): " + config.getOption("someList").getListValue().get(0));

        try {
            someLong.setValue("123"); // this will throw because the original value is a long
        } catch (InvalidOptionException exception) {
            System.out.println(exception.getMessage());
        }

        someLong.setRawValue("123"); // this will work because it changes the object entirely.. Not sure why you'd want this but here it is.

        System.out.println("testConfig.someLong (after again): " + config.getOption("someLong"));

        config.markDirty();
        config.save();


    }

}
