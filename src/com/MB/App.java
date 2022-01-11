package com.MB;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public final class App {
    /**
     * The actual program.
     */
    public void run() {

        HitTableList world = new HitTableList();
        world.add(new Sphere(new Vec3(0,0,-1),.5f));
        world.add(new Sphere(new Vec3(0,-100.5f,-1),100f));
        // Image
        final float aspectRatio = 16.0f / 9.0f;
        final int imageWidth = 400;
        final int imageHeight = (int) (imageWidth / aspectRatio);
        final int samplesPerPixel = 100;
        // Camera

       Camera camera = new Camera();


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
                    Vec3 pixelColour = new Vec3(0,0,0);
                    for (int samples = 0; samples < samplesPerPixel; samples++) {
                        float u = (float) (i) / (imageWidth - 1);
                        float v = (float) (j) / (imageHeight - 1);
                        Ray ray = camera.getRay(u, v);

                        pixelColour.addEquals(
                                rayColor(ray, world));
                    }

                    fw.write(String.format("%s%s", PPM.vectorToRGB(pixelColour,samplesPerPixel), System.lineSeparator()));
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
    public Vec3 rayColor(final Ray r,final HitTableList world) {
        HitRecord hitRecord = new HitRecord();
        // smallest hit point aka t
        if (world.hit(r,0,Utils.Constants.infinity,hitRecord)){
            return  (new Vec3(1,1,1).add(hitRecord.normal)).scale(.5f);
        }
        Vec3 unitDirection = Vec3.normalize(r.direction);
          float t = 0.5f * (unitDirection.y) + 1.0f;
           final Vec3 a = new Vec3(1.0f, 1.0f, 1.0f);
           final Vec3 b = new Vec3(0.5f, 0.7f, 1.0f);
           return Vec3.lerp(a, b, t);
    }

    /**
     * Calculates if the sphere has been hit.
     * Using a simplified Quadratic Equation.
     *
     * @param centreOfSphere The Vector for the Sphere.
     * @param radius         the radius of the sphere.
     * @param ray            the Ray to use.
     * @return if the ray hit the sphere.
     */
    public float hitSphere(final Vec3 centreOfSphere,
                           final float radius,
                           final Ray ray) { // cos stands for Centre of Sphere
        // Create Vector for calculation from origin and centre
        Vec3 oc = ray.origin().subtract(centreOfSphere);
        // Set up Quadratic formulae for Discriminant
        float a = ray.direction().lengthSquared();
        float halfB = Vec3.dot(oc, ray.direction());
        float c = oc.lengthSquared() - (radius * radius);
        // Return if the ray hit the sphere
        float discriminant = halfB * halfB -  a * c;
        if (discriminant < 0) {
            return -1;
        } else {
            return (float) ((-halfB - Math.sqrt(discriminant)) / a);
        }
    }
}
