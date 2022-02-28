package com.MB;

public class Metal extends Material{
    float fuzz;
    public Metal(Vec3 albedo,float f) {
        this.albedo = albedo;
        fuzz = f < 1 ? f :1;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {
        Vec3 reflected = Vec3.reflect(Vec3.normalize(rayIn.direction),hitRecord.normal);
        scattered.set(new Ray(hitRecord.point,reflected.add(Vec3.randomInUnitSphere().scale(fuzz))));
        attenuation.set(albedo);
        return (Vec3.dot(scattered.direction(), hitRecord.normal) > 0);
    }
}
