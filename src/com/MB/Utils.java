package com.MB;

import java.util.Random;

public class Utils
{
    public static class Constants{
        public final static float infinity = Float.POSITIVE_INFINITY;
        public final static float pi = (float) Math.PI;
    }

    /**
     * Degrees to Radians, converts Degrees to Radians
     * @param degrees - amount to Convert
     * @return the amount of radians
     */
   public float degreesToRadians(float degrees){
        return degrees * Constants.pi / 180F;
    }

    /** Returns a Random Float */
    public float randomFloat(float min,float max){
        Random rand = new Random();
        return min + (max - min) * rand.nextFloat();
    }

    /** Clamp Number Between max and min **/
    public static float clamp(float x, float min,float max){
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
}
