package com.MB;

public class HitRecord {
    public Vec3 point; // The Point Where It Hit
    public Vec3 normal; // The Normal Where It Hit
    public float t; //hits
    public boolean frontFace; // FrontFace or BackFace
    public Material material;

    /**
     * Base Constructor
     */
    public HitRecord(){
        this.point = new Vec3();
        this.t = 0;
        this.normal = new Vec3();
    }

    /**
     * Set Face Normal
     * @param r - Incoming Ray
     * @param outwardNormal - The Normal To Check
     */
    void setFaceNormal(Ray r,Vec3 outwardNormal){
        frontFace = Vec3.dot(r.direction(),outwardNormal) < 0;
        normal.set ( frontFace ? outwardNormal : outwardNormal.neg());
    }

    /**
     * Set
     * <p>Sets the Variables in one call</p>
     * @param other - The Other HitRecord to get the Variables from
     */
    void set(HitRecord other) {
        // Deep copy: copy the float values, not the Vec3 references.
        // After this, this.normal and other.normal are independent objects
        // that happen to hold the same values — modifying one won't affect the other.
        this.point.set(other.point);   // copies x, y, z values into our existing Vec3
        this.normal.set(other.normal); // same here
        this.t = other.t;
        this.material = other.material; // Material is fine to share — it's never mutated during rendering
        this.frontFace = other.frontFace;
    }


}
