package net.thedudemc.dudeconfig.examples;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.examples.object.SomeObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestConfig extends Config {

    @Expose private String someString;
    @Expose private List<SomeObject> someObjects;

    @Override
    public String getName() {
        return "testConfig";
    }

    @Override
    protected Config getDefault() {
        TestConfig config = new TestConfig();
        config.reset();
        return config;
    }

    @Override
    public void reset() {
        someString = "Hello!";

        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(1, 100);
        map.put(2, 300);
        map.put(3, 500);
        map.put(4, 700);

        someObjects = new ArrayList<>();
        someObjects.add(new SomeObject("Thing1", 123, .1f, map));
        someObjects.add(new SomeObject("Thing2", 456, .2f, null));
        someObjects.add(new SomeObject("Thing3", 789, .3f, null));
    }

    public List<SomeObject> getSomeObjects() {
        return someObjects;
    }

    public SomeObject getSomeObject(String name) {
        for (SomeObject someObject : someObjects) {
            if (name.equalsIgnoreCase(someObject.getName())) return someObject;
        }
        return null;
    }
}
