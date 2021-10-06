package com.MB;

public final class PPM {
    /** Number to multiply to get RGB. **/
    private static float multiplier = 255.999f;
        /**
     * To RGB().
     * Prints the Vector as an RGB String.
     * <p>
     * Connected to the vector for easier debugging.
     * </p>
     *
     * @return a string version of the Vector.
     * @param vector Vector to turn into RGB String
     */
    public static String vectorToRGB(final Vec3 vector) {
        return String.format("%d %d %d",
                getRedValue(vector),
                getGreenValue(vector),
                getBlueValue(vector));
    }

    /**
     * Calculates the red value and returns the blue value.
     * @param vector - vector to get red value from.
     * @return the value.
     */
    public static int getRedValue(final Vec3 vector) {
        return (int) (vector.x * multiplier);
    }

    /**
     * Calculates the green value.
     * @param vector - vector to get blue value from.
     * @return the green value.
     */
    public static int getGreenValue(final Vec3 vector) {
        return (int) (vector.y * multiplier);
    }

    /**
     * Calculates the blue value.
     * @param vector - vector to get blue value from.
     * @return the blue value.
     */
    public static int getBlueValue(final Vec3 vector) {
        return (int) (vector.z * multiplier);
    }
}
