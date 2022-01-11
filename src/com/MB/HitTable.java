package com.MB;

public interface HitTable {
    /**
     * Hit
     * <p>Detects if a ray hit the sphere</p>
     * @param ray - The Incoming Ray
     * @param tMin - Minimum no of Hits
     * @param tMax - Maximum no of Hits
     * @param rec - Hit Record
     * @return Whether the ray hit the sphere
     */
    public boolean hit(final Ray ray, float tMin, float tMax, HitRecord rec);
}

