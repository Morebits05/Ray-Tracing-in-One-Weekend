package Tests;

import com.MB.Vec3;

import java.util.Random;

public class TestUtil {
    public final static int LOW = -100;
    public final static int HIGH = 100;
    private Random gen = new Random();

    public float getFloatInRange(float l, float h) {
        return gen.nextFloat() * (h - l) + l;
    }

    public Vec3 getRandomPointOnUnitSphere() {
        Vec3 retVal = new Vec3(getFloatInRange(-1, 1),
                getFloatInRange(-1, 1),
                getFloatInRange(-1, 1));
        return Vec3.normalize(retVal);
    }

    public float getRandomRadius(float low, float high) {
        return getFloatInRange(low, high);
    }

    public Vec3 getSphereOrigin(float low, float high) {
        return new Vec3(
                getFloatInRange(low, high),
                getFloatInRange(low, high),
                getFloatInRange(low, high));
    }

}
