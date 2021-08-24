package com.MB;

public class Ray {
    public Vec3 origin;
    public Vec3 direction;

    public Ray(){
        this.origin = new Vec3();
        this.direction = new Vec3();
    }

    public Ray(Vec3 newOrigin, Vec3 newDirection){
       origin = newOrigin;
       direction = newDirection;
    }

    public void set(Ray r) {
       origin = r.origin;
       direction = r.direction;
    }
}
