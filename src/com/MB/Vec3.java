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

    public Vec3 scale(double t) {
        return new Vec3((int) (x * t), (int) (y * t), (int) (z * t));
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

    public Vec3 divideEqual(Vec3 other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        return this;
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
