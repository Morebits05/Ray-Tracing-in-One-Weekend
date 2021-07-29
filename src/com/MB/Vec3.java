package com.MB;


import java.util.Objects;

public class Vec3{
    public float X = -1;
    public float Y = -1;
    public float Z = -1;
    public Vec3(float x, float y, float z){
        X = x;Y=y;Z=z;
    }
    public Vec3(){
        X = 0;
        Y = 0;
        Z = 0;
    }

    @Override
    public String toString() {
        return "<"+X+","+Y+","+Z+">";
    }

    @Override
    public boolean equals(Object o) {
       if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3 vec3 = (Vec3) o;
        return X == vec3.X && Y == vec3.Y && Z == vec3.Z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y, Z);
    }


    public Vec3 add(Vec3 other){
        return new Vec3(X + other.X,Y + other.Y,Z + other.Z);
    }

    public Vec3 subtract(Vec3 other) {
        return new Vec3(X - other.X,
                        Y- other.Y,
                        Z- other.Z);
    }

    public Vec3 scale(Vec3 other) {
        return new Vec3(X * other.X,Y * other.Y,Z* other.Z);
    }

    public Vec3 scale(double t) {
        return new Vec3((int) (X * t),(int)(Y*t),(int)(Z*t));
    }


    public Vec3 addEquals(Vec3 other) {
        this.X += other.X;
        this.Y += other.Y;
        this.Z += other.Z;
        return this;
    }

    public Vec3 scaleEquals(Vec3 other) {
        this.X *= other.X;
        this.Y *= other.Y;
        this.Z *= other.Z;
        return this;
    }

    public Vec3 divideEqual(Vec3 other){
        this.X /= other.X;
        this.Y /= other.Y;
        this.Z /= other.Z;
        return this;
    }

    public Vec3 subtractEquals(Vec3 other) {
        this.X -= other.X;
        this.Y -= other.Y;
        this.Z -= other.Z;
        return this;
    }

    public float lengthSquared() {
        return (float)  (X*X) + (Y*Y) + (Z*Z)  ;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }
}
