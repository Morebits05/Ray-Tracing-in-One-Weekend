package com.MB;

import static com.MB.Vec3.cross;
import static com.MB.Vec3.randomInUnitSphere;

public class Camera {
    public Camera(Vec3 lookFrom,
                  Vec3 lookAt,
                  Vec3 vup,
                  float verticalFieldOfView
                  ,float aspectRatio
                  ,float aperture
                  ,float focusDist){

        float theta = Utils.degreesToRadians(verticalFieldOfView);
        float h = (float) Math.tan(theta/2);
        float viewportHeight = 2.0F * h;
        float viewportWidth = aspectRatio * viewportHeight;

          w = Vec3.normalize(lookFrom.subtract(lookAt));
          u = Vec3.normalize(
                cross(vup,w));
          v = cross(w,u);

        origin = new Vec3(lookFrom);
        horizontal = (u.scale(viewportWidth)).scale(focusDist);
        vertical = (v.scale(viewportHeight)).scale(focusDist);

        lowerLeftCorner = origin.subtract(Vec3.divide(horizontal,2))
                .subtract(Vec3.divide(vertical,2))
                .subtract(w.scale(focusDist));
        lensRadius = aperture / 2;
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
        Vec3 rd = randomInUnitSphere().scale(lensRadius);
        Vec3 offset = u.scale(rd.x).add(v).scale(rd.y);

        return new Ray(origin.add(offset),lowerLeftCorner.add(horizontal.scale(s))
                .add(vertical.scale(t)
                .subtract(origin)).subtract(offset));
    }
    private Vec3 origin;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 lowerLeftCorner;
    private Vec3 u,v,w;
    float lensRadius;
}
