package com.MB;


import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class App {



    /**
     * The actual program.
     **/


    public void  run() {

        HitTableList world = new HitTableList();
        // Image
        float aspectRatio = 16.0f / 9.0f;
        int imageWidth = 400;
        int imageHeight = Math.max(1, (int) Math.round((double) imageWidth / aspectRatio));
        int samplesPerPixel = 10;
        int maxDepth = 50;
        // Generate World
        world = randomScene();
        // Camera
        Vec3 lookFrom = new Vec3(13F, 2F, 3F);
        Vec3 lookAt = new Vec3(0F, 0F, 0F);
        Vec3 vup = new Vec3(0F, 1F, 0F);

        float distToFocus = 10F;
        float aperture = 0.1F;

        Camera camera = new Camera(lookFrom,
                lookAt,
                vup
                , 20
                , aspectRatio
                , aperture
                , distToFocus);


        // Write to Working Directory
        StringBuilder sb = new StringBuilder();
        sb.append(Paths.get(".").toAbsolutePath().normalize());
        sb.append("\\image.ppm");

        Ray ray;

            // Flatten the 2D stream into a single sequence of pixel strings,
// then join them all with a single consistent newline.
// There's no meaningful difference between pixels "within" a row
// and pixels "between" rows in the PPM format — it's just a flat list.
        try (FileWriter fw = new FileWriter(sb.toString())) {
            fw.write("P3\n");
            fw.write(imageWidth + " " + imageHeight + "\n255\n");
            // Capture the final state of world in a new variable.
// Java sees this is assigned exactly once and never changed,
// making it safe to use inside lambdas.

            for (int i = 0; i < imageHeight; i++) {
                final int row = i;

                HitTableList finalWorld = world;
                List<String> rowPixels = IntStream.range(0, imageWidth)
                        .parallel()
                        .mapToObj(j -> getPPMPixelColor(row, j, imageWidth, imageHeight,
                                camera, finalWorld, samplesPerPixel, maxDepth))
                        .collect(Collectors.toList());

                for (String pixel : rowPixels) {
                    fw.write(pixel);
                    fw.write("\n");
                }
            }
    } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    };

    /**
     * RayColor.
     * Calculates the colour of the pixel.
     * @param ray - Ray to use
     * @return The Background Colour if no hit,
     * else returns the Color of the object.
     */
    public static Vec3 rayColor(final Ray ray, final HitTableList world, int depth) {
        HitRecord hitRecord = new HitRecord();
        // If the depth is 0, there's no light
        if (depth <= 0)
            return new Vec3(0, 0, 0);

        if (world.hit(ray,0.001F,Float.POSITIVE_INFINITY,hitRecord)) {
            Ray scattered = new Ray();
            Vec3 attenuation = new Vec3();
            if (hitRecord.material.scatter(ray, hitRecord, attenuation, scattered))
                return attenuation.scale(rayColor(scattered, world, depth - 1));
            return new Vec3(0, 0, 0);
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
    public static String getPPMPixelColor(int i, int j, int imageWidth, int imageHeight,
                                          Camera camera, HitTableList world,
                                          int samplesPerPixel, int depth) {
        Vec3 pixelColour = new Vec3(0, 0, 0);

        for (int s = 0; s < samplesPerPixel; s++) {
            float u = (j + Utils.randomFloat(0.0f, 1.0f)) / (float)(imageWidth - 1);
            float v = 1.0f - (i + Utils.randomFloat(0.0f, 1.0f)) / (float)(imageHeight - 1);
            final Ray rayP = camera.getRay(u, v);
            pixelColour.addEquals(App.rayColor(rayP, world, depth));
        }

        String result = PPM.vectorToRGB(pixelColour, samplesPerPixel);

        String[] tokens = result.trim().split("\\s+");
        if (tokens.length != 3) {
            // Write bad pixels to a separate log file we can actually read
            try (FileWriter log = new FileWriter("badpixels.txt", true)) { // true = append mode
                log.write("BAD PIXEL at (" + i + "," + j + "): '"
                        + result + "' — " + tokens.length + " tokens, colour was "
                        + pixelColour + "\n");
            } catch (Exception ex) {
                // ignore logging errors
            }
        }

        return result;
    }
}
