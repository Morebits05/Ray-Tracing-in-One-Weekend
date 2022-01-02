package com.MB;

public interface HitTable {
    public boolean hit(final Ray ray, float tMin, float tMax, HitRecord rec);
}

