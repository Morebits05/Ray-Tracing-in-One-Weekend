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

        Material matGround = new Lambertian(new Vec3(0.8f,0.8f,0.0f));
        Material matCentre = new Lambertian(new Vec3(0.1f,0.2f,0.5f));
        Material matLeft = new Dielectric(1.5f);
        Material matRight = new Metal(new Vec3(0.8f,0.6f,0.2f),0.0f);


        world.add(new Sphere(new Vec3(0.0f,-100.5f,-1.0f),100.0f,matGround));
        world.add(new Sphere(new Vec3(0.0f,0.0f,-1.0f),0.5f,matCentre));
        world.add(new Sphere(new Vec3(-1.0f,0.0f,-1.0f),0.5f,matLeft));
        world.add(new Sphere(new Vec3(-1.0f,0.0f,-1.0f),-0.45f,matLeft));
        world.add(new Sphere(new Vec3(1.0f,0.0f,-1.0f),0.5f,matRight));

        // Image
        float aspectRatio = 16.0F / 9.0F;
        int imageWidth = 400;
        int imageHeight = (int) (imageWidth / aspectRatio);
        int samplesPerPixel = 100;
        int maxDepth = 50;
        // Camera

       Camera camera = new Camera(new Vec3(-2f,2f,1f),
        new Vec3(0,0,-1),
               new Vec3(0,1,0),
               20,aspectRatio);


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
            Ray ray = new Ray();
            for (int j = imageHeight - 1; j >= 0; --j) { // y value
                System.err.println("Scan lines remaining: " + j + " ");
                System.err.flush();

                for (int i = 0; i < imageWidth; ++i) { // x value
                    Vec3 pixelColour = new Vec3(0,0,0);
                    for (int samples = 0; samples < samplesPerPixel; ++samples) {
                       float u = (i + Utils.randomFloat(0.0f,1.0f))  / (float) (imageWidth - 1);
                       float v = (j + Utils.randomFloat(0.0f,1.0f))  / (float) (imageHeight - 1);
                        ray = camera.getRay(u, v);

                        pixelColour.addEquals(rayColor(ray,world,maxDepth));
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
     * @param ray - Ray to use
     * @return The Background Colour if no hit,
     * else returns the Color of the object.
     */
    public Vec3 rayColor(final Ray ray,final HitTableList world,int depth) {
        HitRecord hitRecord = new HitRecord();
        // If the depth is 0, there's no light
        if (depth <= 0)
            return new Vec3(0, 0, 0);

        if (world.hit(ray,0.001F,Float.POSITIVE_INFINITY,hitRecord)){
            Ray scattered = new Ray();
            Vec3 attenuation = new Vec3();
            if (hitRecord.material.scatter(ray,hitRecord,attenuation,scattered))
                return attenuation.scale(rayColor(scattered,world,depth-1));
            return new Vec3(0,0,0);
        }

           Vec3 unitDirection = Vec3.normalize(ray.direction);
           float t = 0.5f * (unitDirection.y + 1.0F);
           final Vec3 a = new Vec3(1.0F, 1.0F, 1.0F);
           final Vec3 b = new Vec3(0.5F, 0.7F, 1.0F);
           return Vec3.lerp(a, b, t);
    }

    public static Ray ray(Vec3 origin, Vec3 direction){
        return new Ray(origin,direction);
    }

}
