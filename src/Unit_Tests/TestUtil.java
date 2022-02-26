package Unit_Tests;

import com.MB.Vec3;

import java.util.Random;

public class TestUtil {
    public final static int LOW = -100;
    public final static int HIGH = 100;
    private Random gen = new Random();

    public float getFloatInRange(final float l, final float h) {
        return gen.nextFloat() * (h - l) + l;
    }

    public float getFloat() {
        return gen.nextFloat();
    }

    public Vec3 getRandomPointOnUnitSphere(final float lower, final float higher) {
        return new Vec3(getFloatInRange(lower, higher),
                getFloatInRange(lower, higher),
                getFloatInRange(lower, higher));
    }

    public Vec3 getRandomPointOnUnitSphere(float radius) {
        return new Vec3(getFloat() * radius,
                getFloat() * radius,
                getFloat()*radius);
    }

    public float getRandomRadius(final float low, final float high) {
        return getFloatInRange(low, high);
    }

    public Vec3 getSphereOrigin(final float low, final float high) {
        return new Vec3(
                getFloatInRange(low, high),
                getFloatInRange(low, high),
                getFloatInRange(low, high));
    }
}
