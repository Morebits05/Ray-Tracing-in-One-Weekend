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
    void set(HitRecord other){
        this.point = other.point;
        this.normal = other.normal;
        this.t = other.t;
        this.material = other.material;
        this.frontFace = other.frontFace;
    }


}
