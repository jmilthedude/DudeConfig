package net.thedudemc.dudeconfig.examples;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.OptionMap;

public class AnotherConfig extends Config {

    @Override
    public String getName() {
        return "anotherConfig";
    }

    @Override
    public OptionMap getDefaults() {
        return OptionMap.create();
    }

}
