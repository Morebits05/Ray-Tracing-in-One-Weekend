package Tests;

import com.MB.Ray;
import com.MB.Sphere;
import com.MB.Vec3;
import com.MB.HitRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class SphereClass {

    Vec3 rayOrigin;
    Vec3 rayDirection;
    Ray testRay;

    @BeforeEach
    public void SetUp(){
        rayOrigin = new Vec3();
        rayDirection = new Vec3();
    }

    @Test
    @DisplayName("Hit Should return False when discriminant is less than 0")
    public void HitTestMethod(){
        rayDirection.z = 1F;

        Sphere s = new Sphere(new Vec3(0F,3F,0F),0.5F);
        HitRecord rec = new HitRecord();
        testRay = new Ray(rayOrigin,rayDirection);

        assertFalse( s.hit(testRay,1F, 1F, rec));
    }

    @Test
    @DisplayName("Hit Should Return False when root is less than tMin")
    public void HitTest2(){
        rayDirection.z = 1F;
        Sphere s = new Sphere(new Vec3(0F,0F,0F),0.5F);
        HitRecord rec = new HitRecord();
        testRay = new Ray(rayOrigin,rayDirection);

        assertFalse( s.hit(testRay,5F, 5F, rec));
    }


    @Test
    @DisplayName("Hit Should Return False When tMax is less than root")
    public void HitTest3(){
        rayDirection.z = 1F;
        Sphere s = new Sphere(new Vec3(0F,0F,0F),0.5F);
        HitRecord rec = new HitRecord();
        testRay = new Ray(rayOrigin,rayDirection);

        assertFalse( s.hit(testRay,1F, -1.0F, rec));
    }


}
