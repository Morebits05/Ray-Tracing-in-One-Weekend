package com.MB;


import java.util.Objects;

public class Vec3{
    public int X = -1;
    public int Y = -1;
    public int Z = -1;
    public Vec3(int x, int y, int z){
        X = x;Y=y;Z=z;
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
        Vec3 tempVec3 =  new Vec3(X + other.X,Y + other.Y,Z + other.Z);
        if (tempVec3.X < -1000)
            tempVec3.X = 1;

        if (tempVec3.Y < -1000)
            tempVec3.Y = 1;

        if (tempVec3.Z < -1000)
            tempVec3.Z = 1;

        return tempVec3;
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
        return new Vec3(X+=other.X,Y+=other.Y,Z+=other.Z);

    }
}
