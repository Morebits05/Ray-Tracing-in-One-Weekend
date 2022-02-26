package com.MB;

import static com.MB.Vec3.dot;

public class Dialectric extends Material {

    public Dialectric(float indexOfRefraction){
        ir = indexOfRefraction;
    }

    public float ir;
    @Override
    public boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {
        attenuation.set(new Vec3(1.0F,1.0F,1.0F));
        float refractionRatio = hitRecord.frontFace ?  (1.0F / ir) : ir;
        Vec3 unitDirection = Vec3.normalize(rayIn.direction());
        Vec3 refracted = refract(unitDirection,hitRecord.normal,refractionRatio);
        scattered.set(new Ray(hitRecord.point,refracted));
        return true;
    }

     public Vec3 refract(Vec3 uv, Vec3 n, float etaiOverEtat) {

        float cosTheta = Math.min(dot(uv.neg(),n),1.0F);
        Vec3 rayOutPerpendicular = (uv.add(n.scale(cosTheta)).scale(etaiOverEtat));
        Vec3 rayOutParallel = n.scale((float) -(Math.sqrt(Math.abs(1.0F-rayOutPerpendicular.lengthSquared()))));
        return rayOutPerpendicular.add(rayOutParallel);
    }
}
