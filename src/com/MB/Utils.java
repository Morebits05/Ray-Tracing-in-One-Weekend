package com.MB;

import java.util.Calendar;
import java.util.Random;

public class Utils
{
    public static class Constants{
        public final static float infinity = Float.POSITIVE_INFINITY;
        public final static float pi = (float) Math.PI;

    }
    public static Random r;
    /**
     * Degrees to Radians, converts Degrees to Radians
     * @param degrees - amount to Convert
     * @return the amount of radians
     */
   public static float degreesToRadians(float degrees){
        return degrees * Constants.pi / 180F;
    }
   public Utils (){

   }
    /** Returns a Random Float */
    public static float randomFloat(float min,float max){
        return min + (max - min) * randomFloat();
    }

    public static float randomFloat(){
         if (r == null)
         {
             r = new Random();
             r.setSeed(Calendar.getInstance().getTimeInMillis());
         }
        return r.nextFloat();
    }

    /** Clamp Number Between max and min **/
    public static float clamp(float x, float min,float max){
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    public float[] vectorToArray(Vec3 vector){
        return new float[]{vector.x,vector.y,vector.z};
    }
}
