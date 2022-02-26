package com.MB;

public abstract class Material {
    public Vec3 albedo;


    protected abstract boolean scatter(final Ray rayIn, final HitRecord hitRecord, Vec3 attenuation, Ray scattered);
}
