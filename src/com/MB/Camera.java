package com.MB;

import static com.MB.Vec3.cross;

public class Camera {
    public Camera(Vec3 lookFrom,
                  Vec3 lookAt,
                  Vec3 vup,
                  float verticalFieldOfView
                  ,float aspectRatio){
        float theta = Utils.degreesToRadians(verticalFieldOfView);
        float h = (float) Math.tan(theta/2);
        float viewportHeight = 2.0F * h;
        float viewportWidth = aspectRatio * viewportHeight;

        Vec3 w = Vec3.normalize(lookFrom.subtract(lookAt));
        Vec3 u = Vec3.normalize(
                cross(vup,w));
        Vec3 v = cross(w,u);

        origin = new Vec3(lookFrom);
        horizontal = u.scale(viewportWidth);
        vertical = v.scale(viewportHeight);

        lowerLeftCorner = origin.subtract(Vec3.divide(horizontal,2))
                .subtract(Vec3.divide(vertical,2))
                .subtract(w);
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

    public Ray getRay(float s, float t) {
        return new Ray(origin,lowerLeftCorner.add(horizontal.scale(s))
                .add(vertical.scale(t)
                .subtract(origin)));
    }
    private Vec3 origin;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 lowerLeftCorner;
}
