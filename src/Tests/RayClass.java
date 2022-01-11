package Tests;

import com.MB.Ray;
import com.MB.Vec3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RayClass {
private TestUtil util;
    @BeforeEach
    public void Before(){
        util = new TestUtil();
    }
    @Test
    @DisplayName("Should have origin and direction")
    public void ClassTest() {
        Ray testClass = new Ray();

        assertNotNull(testClass.origin);
        assertNotNull(testClass.direction);
    }

    @Test
    @DisplayName("Constructor should set Vectors with variables")
    public void constructorTest() {
        for (int tests = 0; tests < 2000; tests++) {

            Vec3 v1 = new Vec3(util.getFloatInRange(-100f, 100f), util.getFloatInRange(-100, 100f), util.getFloatInRange(-100f, 100f));
            Vec3 v2 = new Vec3(util.getFloatInRange(-100f, 100f), util.getFloatInRange(-100, 100f), util.getFloatInRange(-100f, 100f));

            Ray ray = new Ray(v1, v2);

            assertEquals(v1.x, ray.origin.x);
            assertEquals(v2.z, ray.direction.z);
        }
    }

    @Test
    @DisplayName("Set Method should set origin and Direction")
    public void setMethodTest() {

        for (int tests = 0; tests < 2000; tests++) {
            Vec3 v1 = new Vec3(util.getFloatInRange(-100, 100),
                    util.getFloatInRange(-100, 100),
                    util.getFloatInRange(-100, 100));

            Vec3 v2 = new Vec3(util.getFloatInRange(-100, 100),
                    util.getFloatInRange(-100, 100),
                    util.getFloatInRange(-100, 100));

            Ray ray = new Ray(v1, v2);

            // Is set called on ray
            v2.set(new Vec3(util.getFloatInRange(-100, 100), util.getFloatInRange(-100, 100), util.getFloatInRange(-100, 100)));
            ray.set(new Ray(v1, v2));
            assertEquals(v2.z, ray.direction.z);


            v2.set(new Vec3(util.getFloatInRange(-100, 100), util.getFloatInRange(-100, 100), util.getFloatInRange(-100, 100)));
            ray.set(new Ray(v1, v2));
            assertEquals(v1.z, ray.origin.z);
        }
    }


    @Test
    @DisplayName("at Method returns Correct Vector 3")
    public void simpleAtMethodTest() {

        // Given origin and direction
        Vec3 origin = new Vec3(3, 2, -4);

        Vec3 direction = new Vec3(3, 2, -2);

        // When ray.at is called
        Ray ray = new Ray(origin, direction);
        final float t = 3.0f;

        // return correct Vector
        Vec3 result = origin.add(direction.scale(t));
        assertEquals(result, ray.at(t));
    }


    @Test
    @DisplayName("at Method works with Random Vectors")
    public void complexAtMethodTest() {

        for (int tests = 0; tests < 2000; tests++) {
            // Given origin and direction
            Vec3 origin = new Vec3(
                    util.getFloatInRange(Vector3.HIGH, Vector3.LOW),
                    util.getFloatInRange(Vector3.HIGH, Vector3.LOW),
                    util.getFloatInRange(Vector3.HIGH, Vector3.LOW));

            Vec3 direction = new Vec3(
                    util.getFloatInRange(Vector3.HIGH, Vector3.LOW),
                    util.getFloatInRange(Vector3.HIGH, Vector3.LOW),
                    util.getFloatInRange(Vector3.HIGH, Vector3.LOW));

            Ray r = new Ray(origin, direction);

            // When ray.at is called
            Ray ray = new Ray(origin, direction);
            final float t = util.getFloatInRange(-10, 10);

            // return correct Vector
            Vec3 result = origin.add(direction.scale(t));
            assertEquals(result, ray.at(t));
        }
    }

    @Test
    @DisplayName("Should have getters")
    public void gettersTest(){
        Vec3 orig = new Vec3(3,2,1);
        Vec3 direc = new Vec3(1,1,2);

        Ray r = new Ray(orig, direc);

        assertEquals(orig,r.origin());
        assertEquals(direc,r.direction());
    }


    @Test
    @DisplayName("Should have setters for both methods")
    public void setterTest3(){


        Vec3 orig = new Vec3(3,2,1);
        Vec3 direc = new Vec3( 1, 1, 4);

        Ray r = new Ray(orig,direc);


        direc = new Vec3(1,1,5);
        orig = new Vec3(1,1,6);
        r.setDirection(direc);
        r.setOrigin(orig);
        assertEquals(5 ,r.direction.z, 0.0);
        assertEquals(6 ,r.origin.z, 0.0);
    }
}
