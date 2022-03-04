package com.MB;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public final class App {
    /**
     * The actual program.
     *
     **/


    public void run() {

        HitTableList world = new HitTableList();

        // Image
        float aspectRatio = 3.0F / 2.0F;
        int imageWidth = 1200;
        int imageHeight = (int) (imageWidth / aspectRatio);
        int samplesPerPixel = 500;
        int maxDepth = 50;


        Instant start;
        Instant end;
        // Generate World
        world = randomScene();




        // Camera
        Vec3 lookFrom = new Vec3(13F,2F,3F);
        Vec3 lookAt = new Vec3(0F,0F,0F);
        Vec3 vup = new Vec3(0F,1F,0F);

        float distToFocus = 10F;
        float aperture = 0.1F;

       Camera camera = new Camera(lookFrom,
                                lookAt,
                                vup
                                ,20
                                ,aspectRatio
                                ,aperture
                                ,distToFocus);


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
            Ray ray;
            start = Instant.now();
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
            end = Instant.now();
            System.err.println(System.lineSeparator());
            System.err.println("Done.");
            System.out.println("Image Size: "+imageHeight + " x "+imageWidth );
            System.out.println("No. of Samples: "+samplesPerPixel);
            System.out.println("Depth:" +maxDepth);
            System.out.println("Time took: "+Duration.between(start,end));
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

    public HitTableList randomScene() {
        HitTableList world = new HitTableList();

        Lambertian groundMat = new Lambertian(new Vec3(0.5f,0.5f,0.5f));
        world.add(new Sphere(new Vec3(0f,-1000f,0f),1000f,groundMat));

        for (int i = -11; i < 11; i++) {
            for (int j = -11; j < 11; j++) {
                float chooseMat = Utils.randomFloat();
                Vec3 centre = new Vec3(i + 0.9f * Utils.randomFloat(),0.2f,j+Utils.randomFloat());

                if ( (centre.subtract( new Vec3(4f,0.2f,0f)).length() > 0.9)){
                    Material sphereMaterial;

                    if (chooseMat < 0.8F){
                        // Diffuse
                        Vec3 albedo = Vec3.generateRandom().scale(Vec3.generateRandom());
                        sphereMaterial = new Lambertian(albedo);
                        world.add(new Sphere(centre,0.2f,sphereMaterial));
                    } else if (chooseMat <0.9F)
                    {
                        // Metal
                        Vec3 albedo = Vec3.generateRandom(0.5f,1.0F);
                        float fuzz = Utils.randomFloat(0f,0.5F);
                        sphereMaterial = new Metal(albedo,fuzz);
                        world.add(new Sphere(centre,0.2f,sphereMaterial));
                    } else {
                        sphereMaterial = new Dielectric(1.5f);
                        world.add(new Sphere(centre,0.2f,sphereMaterial));
                    }
                }
            }
        }
        Material dielectric = new Dielectric(1.5f);
        world.add(new Sphere(new Vec3(0f,1f,0f),1.0f,dielectric));

        Material lambertian = new Lambertian(new Vec3(0.4f,0.2f,0.1f));
        world.add(new Sphere(new Vec3(-4f,1f,0f),1.0f,lambertian));

        Material metal = new Metal(new Vec3(0.7f,0.6f,.5f),0.0f);
        world.add(new Sphere(new Vec3(4f,1f,0f),1.0f,metal));


        return world;
    }
}
