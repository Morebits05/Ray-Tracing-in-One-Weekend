package com.MB;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public final class App {
    /**
     * The actual program.
     *
     **/


    public void run() {

        HitTableList world = new HitTableList();
        world.add(new Sphere(new Vec3(0,0,-1),.5f));
        world.add(new Sphere(new Vec3(0,-100.5f,-1),100f));
        // Image
        final float aspectRatio = 16.0f / 9.0f;
        final int imageWidth = 400;
        final int imageHeight = (int) (imageWidth / aspectRatio);
        final int samplesPerPixel = 100;
        int maxDepth = 50;
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
                    for (int samples = 0; samples < samplesPerPixel; ++samples) {
                       float u = (float) ((i) + Utils.randomFloat())  / (imageWidth - 1);
                        float v = (float) ((j) + Utils.randomFloat())  / (imageHeight - 1);
                        Ray ray = camera.getRay(u, v);

                        pixelColour.addEquals(
                                rayColor(ray, world,maxDepth));
                    }

                    fw.write(String.format("%s%s", PPM.vectorToRGB(pixelColour,samplesPerPixel), System.lineSeparator()));
                }
            }
            fw.close();
            System.err.println(System.lineSeparator());
            System.err.println("Done.");
        } catch ( IOException ex) {
            System.out.println(ex.getMessage());
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
    public Vec3 rayColor(final Ray r,final HitTableList world,int depth) {
        HitRecord hitRecord = new HitRecord();
        // If the depth is 0, there's no light
        if (depth <= 0)
            return new Vec3(0,0,0);


        // smallest hit point aka t
        if (world.hit(r,0,Utils.Constants.infinity,hitRecord)){
            Vec3 targetPoint = hitRecord.point.add(  (hitRecord.normal.add(Vec3.randomInUnitSphere()) )) ;
            return rayColor(new Ray(hitRecord.point,(targetPoint.subtract(hitRecord.point))),world,depth-1).scale(.5F);
        }

        Vec3 unitDirection = Vec3.normalize(r.direction);
          float t = 0.5f * (unitDirection.y) + 1.0f;
           final Vec3 a = new Vec3(1.0f, 1.0f, 1.0f);
           final Vec3 b = new Vec3(0.5f, 0.7f, 1.0f);
           return Vec3.lerp(a, b, t);
    }
}
