package com.MB;
public final class PPM {
    /** Number to multiply to get RGB. **/
    /**
     * To RGB().
     * Prints the Vector as an RGB String.
     * <p>
     * Connected to the vector for easier debugging.
     * </p>
     *
     * @param vector Vector to turn into RGB String
     * @return a string version of the Vector.
     */
    public static String vectorToRGB(final Vec3 vector,int samplesPerPixel) {
        float red = vector.x;
        float green = vector.y;
        float blue = vector.z;

        float scale = 1.0F / samplesPerPixel;

        red *= scale;
        green *= scale;
        blue *= scale;

        return String.format("%d %d %d",
                (int) (256 * Utils.clamp(red,0.0F,0.999F)),
                (int) (256 * Utils.clamp(green, 0.0F,0.999F)),
                (int) (256 * Utils.clamp(blue,0.0F,0.999F)));
    }
}
