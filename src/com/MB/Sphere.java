package com.MB;


public class Sphere implements HitTable{




    /**
     * Base Constructor
     * @param center - The Vector for the Center of the Sphere
     * @param radius - The Radius of the Sphere
     * @param material - The Material of the Sphere
     */

    public Sphere(Vec3 center, float radius, Material material){
        this.centerSphere = center;
        this.radius = radius;
        this.material = material;
    }

    public Sphere(Vec3 center, float radius) {
        this(center, radius,new Lambertian(new Vec3(0.8F,0.8F,0.0F)));
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
    public boolean hit(Ray ray, float tMin, float tMax, HitRecord rec){
        // Create Vector for calculation from origin and centre
        Vec3 centerOfOrigin = ray.origin.subtract(centerSphere);
        // Set up Quadratic formulae for Discriminant
        float a = ray.direction.lengthSquared();
        float halfB = Vec3.dot(centerOfOrigin, ray.direction);
        float c = centerOfOrigin.lengthSquared() - (radius * radius);

        // Return if the ray hit the sphere
        float discriminant = halfB * halfB - a * c;
        if (discriminant < 0)
            return false;

        float sqrtd = (float) Math.sqrt(discriminant);

        float root = (-halfB - sqrtd) / a;
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtd) / a;
            if (root < tMin || tMax < root)
                return false;
        }

            rec.t = root;
            rec.point.set(ray.at(rec.t));
            Vec3 outWardNormal = Vec3.divide((rec.point.subtract(centerSphere)), radius);
            rec.setFaceNormal(ray, outWardNormal);
            rec.material = material;

        return true;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "centerSphere=" + centerSphere +
                ", radius=" + radius +
                ", material=" + material +
                '}';
    }


    /**
     * The Center of the Sphere
     * */
    public Vec3 centerSphere;
    /**
     * The Radius of the Sphere
     */
    public float radius;
    /** The Material of the Sphere */
    public Material material;
}
