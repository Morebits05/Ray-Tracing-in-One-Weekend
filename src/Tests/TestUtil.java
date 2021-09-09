package Tests;

import java.util.Random;

public class TestUtil {
    private Random gen = new Random();
     public final static int LOW = -100;
     public final static int HIGH = 100;
    public  float getFloatInRange(float l, float h) {
        return gen.nextFloat() * (h - l) + l;
    }
}
