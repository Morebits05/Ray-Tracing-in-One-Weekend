package com.MB;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public final class App {
    /**
     * The actual program.
     */
    public void run() {
        // Image
        final float aspectRatio = 16.0f / 9.0f;
        final int imageWidth = 400;
        final int imageHeight = (int) (imageWidth / aspectRatio);

        // Camera

        float viewportHeight = 2.0F;
        float viewportWidth = aspectRatio * viewportHeight;
        float focalLength = 1.0F;

        Vec3 origin = new Vec3(0, 0, 0);
        Vec3 horizontal = new Vec3(viewportWidth, 0, 0);
        Vec3 vertical = new Vec3(0, viewportHeight, 0);
        Vec3 lowerLeftCorner = ((origin
                .subtract((Vec3.divide(horizontal, 2)))
                .subtract((Vec3.divide(vertical, 2)))
                .subtract(new Vec3(0, 0, focalLength))));


        // Write to Working Directory
        StringBuilder sb = new StringBuilder();
        sb.append(Paths.get(".").toAbsolutePath().normalize());
        sb.append("\\image.ppm");
        // Render
        try {
            FileWriter fw = new FileWriter(sb.toString());
            fw.write("P3" + System.lineSeparator());
            fw.write(imageWidth + " "
                    + imageHeight + System.lineSeparator()
                    + "255" + System.lineSeparator());
            for (int j = imageHeight - 1; j >= 0; --j) { // y value
                System.err.println("Scan lines remaining: " + j + " ");
                System.err.flush();
                for (int i = 0; i < imageWidth; ++i) { // x value
                    float u = (float) (i) / (imageWidth - 1);
                    float v = (float) (j) / (imageHeight - 1);
                    Ray ray = new Ray(origin, (((lowerLeftCorner
                            .add((horizontal.scale(u))
                                    .add((vertical.scale(v)))
                                    .subtract(origin))))));

                    Vec3 pixelColor = rayColor(ray);

                    fw.write(String.format("%s%s", PPM.vectorToRGB(pixelColor), System.lineSeparator()));
                }
            }
            fw.close();
            System.err.println(System.lineSeparator());
            System.err.println("Done.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * RayColor.
     * Calculates the colour of the pixel.
     *
     * @param r - Ray to use
     * @return The Background Colour if no hit,
     * else returns the Color of the object.
     */
    public Vec3 rayColor(final Ray r) {
       /* if (hitSphere(new Vec3(0, 0, -1), 0.5f, r)) {
            return new Vec3(1, 0, 0);
        }*/

        Vec3 unitDirection = Vec3.normalize(r.direction);
        float t = 0.5f * (unitDirection.y) + 1.0f;
        return ((new Vec3(1.0f, 1.0f, 1.0f).scale(1.0F - t)
                .add(new Vec3(0.5f, 0.7f, 1.0f).scale(t))));
    }

    /**
     * Calculates if the sphere has been hit.
     *
     * @param centreOfSphere The Vector for the Sphere.
     * @param radius         the radius of the sphere.
     * @param ray            the Ray to use.
     * @return if the ray hit the sphere.
     */
    public boolean hitSphere(final Vec3 centreOfSphere,
                             final float radius,
                             final Ray ray) { // cos stands for Centre of Sphere
        // Create Vector for calculation from origin and centre
        Vec3 oc = (ray.origin()).subtract(centreOfSphere);
        // Set up Quadratic formulae for Discriminant
        float a = Vec3.dot(ray.direction(), ray.direction());
        float b = 2.0f * Vec3.dot(oc, ray.direction());
        float c = Vec3.dot(oc, oc) - (radius * radius);
        // Return if the ray hit the sphere
        float discriminant = b * b - 4 * a * c;
        return (discriminant > 0);
    }
}
