package com.MB;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class App {
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
        Vec3 lowerLeftCorner = origin
                .subtract(Vec3.divide(horizontal, 2))
                .subtract(Vec3.divide(vertical, 2))
                .subtract(new Vec3(0, 0, focalLength));

        // Render
        StringBuffer sb = new StringBuffer();
        sb.append(Paths.get(".").toAbsolutePath().normalize());
        sb.append("\\image.ppm");

        try {
            FileWriter fw = new FileWriter(sb.toString());
            fw.write("P3"+ System.lineSeparator());
            fw.write(imageWidth + " " + imageHeight + System.lineSeparator() + "255" + System.lineSeparator());
            for (int j = imageHeight-1; j >= 0; --j) {
                System.err.println("Scan lines remaining: " + j + " ");  System.err.flush();
                for (int i = 0; i < imageWidth; ++i) {
                    float u =   (float) i / imageWidth - 1;
                    float v =  (float) j / imageHeight - 1;
                    Ray ray = new Ray(origin, lowerLeftCorner.add(horizontal).scale(u)
                            .add(vertical).scale(v).subtract(origin));


                    Vec3 pixel_color = rayColor(ray);
                    fw.write(pixel_color.toRGB() + System.lineSeparator());
                }
            }
            fw.close();
            System.err.println(System.lineSeparator());
            System.err.println("Done.");
        } catch (IOException e){
         // Do Something with e
        }
            }



    public Vec3 rayColor(Ray r){
        Vec3 unitDirection = Vec3.normalize(r.direction);
        float t = 0.5F * (unitDirection.y)+1.0F;
        return new Vec3(1.0f,1.0f,1.0f).scale(1.0F-t)
                .add( new Vec3(0.5f,0.7f,1.0f).scale(t));
    }


}
