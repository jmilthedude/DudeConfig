package net.thedudemc.dudeconfig.config.option;

import java.util.HashMap;

public class OptionMap extends HashMap<String, Option<?>> {

    private OptionMap() {
    }

    public static OptionMap create() {
        return new OptionMap();
    }

}
