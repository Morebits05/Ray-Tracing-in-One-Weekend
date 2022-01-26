package com.MB;

public class Sphere implements HitTable{

    /**
     * Base Constructor
     * @param center - The Vector for the Center of the Sphere
     * @param radius - The Radius of the Sphere
     */
    public Sphere(Vec3 center, float radius){
        this.centerSphere = center;
        this.radius = radius;
    }

    /**
     * Hit
     * <p>Detects if a ray hit the sphere</p>
     * @param ray - The Incoming Ray
     * @param tMin - Minimum no of Hits
     * @param tMax - Maximum no of Hits
     * @param rec - Hit Record
     * @return Whether the ray hit the sphere
     */
    @Override
    public boolean hit(Ray ray, float tMin, float tMax, HitRecord rec) {
        // Create Vector for calculation from origin and centre
        Vec3 centerOfOrigin = ray.origin.subtract(centerSphere);
        // Set up Quadratic formulae for Discriminant
        final float a = ray.direction.lengthSquared();
        final float halfB = Vec3.dot(centerOfOrigin,ray.direction);
        final float c = centerOfOrigin.lengthSquared()-(radius*radius);

        // Return if the ray hit the sphere
        final float discriminant = halfB * halfB - a * c;
        if (discriminant < 0) return false;

        float discRoot = (float) Math.sqrt(discriminant);

        float root = (-halfB - discRoot) / a;
        if (root < tMin || tMax < root){
            root = (-halfB + discRoot) / a;
            if (root < tMin || tMax <root)
                return false;
        }

        rec.t = root;
        rec.point.set(ray.at(rec.t));
        Vec3 outwardNormal = Vec3.divide(rec.point.subtract(centerSphere),radius);
        rec.setFaceNormal(ray,outwardNormal);
        return true;
    }

    /**
     * The Center of the Sphere
     * */
    public Vec3 centerSphere;
    /**
     * The Radius of the Sphere
     */
    public float radius;
}
