package com.MB;

public class Camera {
    public Camera(){
        float aspectRatio = 16.0F / 9.0F;
        float viewportHeight = 2.0F;
        float viewportWidth = aspectRatio * viewportHeight;
        float focalLength = 1.0F;

        origin = new Vec3(0F,0F,0F);
        horizontal = new Vec3(viewportWidth, 0.0F, 0.0F);
        vertical = new Vec3(0.0F,viewportHeight,0.0F);

        lowerLeftCorner = origin.subtract(Vec3.divide(horizontal,2))
                .subtract(Vec3.divide(vertical,2))
                .subtract(new Vec3(0,0,focalLength));
    }

    @Override
    public String toString() {
        return "Camera{" +
                "origin=" + origin +
                ", horizontal=" + horizontal +
                ", vertical=" + vertical +
                ", lowerLeftCorner=" + lowerLeftCorner +
                '}';
    }

    public Ray getRay(float u, float v) {
        return new Ray(origin,lowerLeftCorner.add(horizontal.scale(u))
                .add(vertical.scale(v))
                .subtract(origin));
    }
    private Vec3 origin;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 lowerLeftCorner;
}
