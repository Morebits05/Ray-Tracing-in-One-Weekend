package com.MB;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static class Constants {
        public final static float infinity = Float.POSITIVE_INFINITY;
        public final static float pi = (float) Math.PI;
    }

    public static float degreesToRadians(float degrees) {
        return degrees * Constants.pi / 180F;
    }

    // No more shared Random field — ThreadLocalRandom manages
    // per-thread instances automatically behind the scenes.
    public static float randomFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    public static float randomFloat(float min, float max) {
        // ThreadLocalRandom has a built-in bounded nextDouble,
        // which is safer than the manual min + (max-min)*random() formula.
        return (float) ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static float clamp(float x, float min, float max) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    public float[] vectorToArray(Vec3 vector) {
        return new float[]{vector.x, vector.y, vector.z};
    }
}
