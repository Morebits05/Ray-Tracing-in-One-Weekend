package com.MB;

import java.util.Vector;

public class HitTableList implements HitTable{

    public Vector<HitTable> objects; // Array Of HitTables

    /**
     * Base Constructor
     */
    public HitTableList() {
        objects = new Vector<HitTable>();
    }

    /**
     * Specialized Constructor
     * @param objectToAdd - Sphere To Add to list
     */
    public HitTableList(HitTable objectToAdd) {
        add(objectToAdd);
    }

    /**
     * Hit
     * <p>Detects if a ray hit the sphere</p>
     * @param ray - The Incoming Ray
     * @param tMin - Minimum no of Hits
     * @param tMax - Maximum no of Hits
     * @param rec - Hit Record
     * @return Whether the ray hit the sphere
     */
    @Override
    public boolean hit(Ray ray, float tMin, float tMax, HitRecord rec) {
        HitRecord temporaryHitRecord = new HitRecord();
        boolean didItHitAnything = false;
        float closestT = tMax;

        for(final HitTable object: objects){
            if (object.hit(ray,tMin,closestT,temporaryHitRecord)){
                didItHitAnything = true;
                closestT = temporaryHitRecord.t;
                rec.set(temporaryHitRecord);
            }
        }
        return  didItHitAnything;
    }

    /**
     * Add
     * <p>Add The HitTable Object to the list</p>
     * @param object - the object to add to the list
     */
    public void add(HitTable object){
        objects.add(object);
    }
}
