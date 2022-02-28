package com.MB;

public class Lambertian extends Material {

    public Vec3 albedo;

    public Lambertian(final Vec3 colour) {
        this.albedo = colour;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {
        Vec3 scatterDirection = hitRecord.normal.add(Vec3.randomUnitVector());

        if (scatterDirection.nearZero())
            scatterDirection = hitRecord.normal;

        scattered.set(new Ray(hitRecord.point,scatterDirection));
        attenuation.set(albedo);
        return true;
    }
}
