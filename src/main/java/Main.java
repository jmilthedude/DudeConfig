import net.thedudemc.dudeconfig.DudeConfig;
import net.thedudemc.dudeconfig.option.Option;

import java.util.Arrays;

public class Main {

    private static boolean running = true;

    public static void main(String[] args) {
        while (running) {
            try {
                TestConfig config = (TestConfig) new TestConfig().readConfig();
                System.out.println(config.getDouble("someDouble"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            running = false;
        }
    }

    public static class TestConfig extends DudeConfig {
        @Override
        public String getName() {
            return "test_config";
        }

        @Override
        protected void reset() {
            options.put("someDouble", Option.of(5).withComment("hello"));
            options.put("someList", Option.of(Arrays.asList("a", "b", "c")));
        }
    }
}
