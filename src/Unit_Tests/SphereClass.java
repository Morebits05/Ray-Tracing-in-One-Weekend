package Unit_Tests;

import com.MB.*;
import com.MB.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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

        Sphere s = new Sphere(new Vec3(0F,3F,0F),0.5F, new Material() {
            @Override
            public boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {
                return false;
            }
        });
        HitRecord rec = new HitRecord();
        testRay = new Ray(rayOrigin,rayDirection);

        assertFalse( s.hit(testRay,1F, 1F, rec));
    }

    @Test
    @DisplayName("Hit Should Return False when root is less than tMin")
    public void HitTest2(){
        rayDirection.z = 1F;
        Sphere s = new Sphere(new Vec3(0F,0F,0F),0.5F, new Material() {
            @Override
            public boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {
                return false;
            }
        });
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

    @Test
    @DisplayName("Sphere Hit should set hit record Material")
    public void SphereHitTest3() {
        HitRecord hr = new HitRecord();
        Sphere sphere1 = new Sphere(new Vec3(0, 0, 0), 0.5f, new Material() {
            @Override
            public boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {
                return false;
            }
        });

        sphere1.hit(new Ray(new Vec3(0,0,0),new Vec3(0,0,-1)),0.001f,Float.POSITIVE_INFINITY,hr);
        assertNotNull(hr.material);
        assertNotNull(sphere1.material);

    }

    @Test
    @DisplayName("Sphere Hit should not set hit record Material when null")
    public void SphereHitTest4() {
        HitRecord hr = new HitRecord();
        Sphere sphere1 = new Sphere(new Vec3(0, 0, 0), 0.5f, null);
        try {
            sphere1.hit(new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, -1)), 0.001f, Float.POSITIVE_INFINITY, hr);
            fail("Should not set hit record material");
        }catch (Exception ex){
            assertNotNull(ex);

        }
    }

    @Test
    @DisplayName("Default Constructor should set base material")
    public void DefaultMaterialTest(){
        Sphere s = new Sphere(new Vec3(0,0,0),.5F);
        assertNotNull(s.material);
    }

}
