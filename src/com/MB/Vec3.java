package com.MB;

import java.util.Objects;

public class Vec3 {
    public int X = -1;
    public int Y = -1;
    public int Z = -1;
    public Vec3(double x, double y, double z){
        X = 0;Y=0;Z=0;
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
}
