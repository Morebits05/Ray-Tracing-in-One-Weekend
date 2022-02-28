package com.MB;

import static com.MB.Vec3.dot;
import static com.MB.Vec3.reflect;

public class Dielectric extends Material {

    public float ir;

    public Dielectric(float indexOfRefraction) {
        ir = indexOfRefraction;
    }

    static float reflectance(float cosine, float refIDX) {
        // Use Schlick's approximation for reflectance
        float r0 = (1 - refIDX) / (1 + refIDX);
        r0 = r0 * r0;
        return (float) (r0 + (1 - r0) * Math.pow((-cosine), 5));
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {
        attenuation.set(new Vec3(1.0F, 1.0F, 1.0F));
        Vec3 unitDirection = Vec3.normalize(rayIn.direction());
        float refractionRatio = hitRecord.frontFace ? (1.0F / ir) : ir;

        float cosTheta = Math.min(dot(unitDirection.neg(), hitRecord.normal), 1.0F);
        float sinTheta = (float) Math.sqrt(1.0f - cosTheta * cosTheta);

        boolean cannotRefract = refractionRatio * sinTheta > 1.0F;
        Vec3 direction;

        if (cannotRefract || reflectance(cosTheta,refractionRatio) > 1.0)
            direction = reflect(unitDirection, hitRecord.normal);
        else {
            direction = refract(unitDirection, hitRecord.normal, refractionRatio);
        }
        scattered.set(new Ray(hitRecord.point, direction));
        return true;
    }

    public Vec3 refract(Vec3 uv, Vec3 n, float etaiOverEtat) {
        float cosTheta = (float) Math.min(dot((uv.neg()), n), 1.0);
        Vec3 rayOutPerpendicular = Vec3.scale(etaiOverEtat, uv.add(n.scale(cosTheta)));
        Vec3 rayOutParallel = n.scale((float) -(Math.sqrt(Math.abs(1.0F - rayOutPerpendicular.lengthSquared()))));
        return rayOutPerpendicular.add(rayOutParallel);
    }
}
