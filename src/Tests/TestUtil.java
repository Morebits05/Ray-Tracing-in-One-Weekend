package Tests;

import java.util.Random;

public class TestUtil {
    private Random gen = new Random();

    public  float getFloatInRange(float l, float h) {
        return gen.nextFloat() * (h - l) + l;
    }
}
