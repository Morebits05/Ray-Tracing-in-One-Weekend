package com.MB;


import java.util.Objects;

public class Vec3 {
    public float x;
    public float y;
    public float z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3() {
        x = 0;
        y = 0;
        z = 0;
    }


    @Override
    public String toString() {
        return "<" + x + "," + y + "," + z + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3 vec3 = (Vec3) o;
        return x == vec3.x && y == vec3.y && z == vec3.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


    public Vec3 add(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 subtract(Vec3 other) {
        return new Vec3(x - other.x,
                y - other.y,
                z - other.z);
    }

    public Vec3 scale(Vec3 other) {
        return new Vec3(x * other.x, y * other.y, z * other.z);
    }

    public Vec3 scale(float t) {
        return new Vec3((x * t),(y * t),(z * t));
    }

    public  static Vec3 scale(float t, Vec3 vec){
        return vec.scale(t);
    }
    public static Vec3  divide(Vec3 vectorToDivide, float t){
        return scale(1/t,vectorToDivide);
    }

    public static Vec3 unit(Vec3 v){
        return divide(v,v.length());
    }

    public Vec3 addEquals(Vec3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vec3 scaleEquals(Vec3 other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public Vec3 scaleEquals(float t) {
        this.x *= t;
        this.y *= t;
        this.z *= t;
        return this;
    }

    public Vec3 divideEqual(float t) {
        return scaleEquals(1/t);
    }

    public Vec3 subtractEquals(Vec3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public float lengthSquared() {
        return (x * x) + (y * y) + (z * z);
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }
}
