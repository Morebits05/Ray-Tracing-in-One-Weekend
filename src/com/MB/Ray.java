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

    public Vec3 at(float t) {
        return origin.add(
                direction.scale(t));
    }

    public Vec3 origin() {
        return origin;
    }

    public Vec3 direction(){
        return direction;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "<" +
                "origin=" + origin +
                ", direction=" + direction +
                '>';
    }

    public void setOrigin(Vec3 origin) {
        this.origin = origin;
    }
}
