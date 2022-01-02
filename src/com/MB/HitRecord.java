package com.MB;

public class HitRecord {
    public Vec3 point;
    public Vec3 normal;
    public float t; //hits
    public boolean frontFace;

    void setFaceNormal(final Ray r,Vec3 outwardNormal){
        frontFace = Vec3.dot(r.direction(),outwardNormal) < 0;
        normal.set ( frontFace ? outwardNormal : outwardNormal.neg());
    }
}
