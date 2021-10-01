package Tests;

import com.MB.App;
import com.MB.Ray;
import com.MB.Vec3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AppClass {

    TestUtil util;
    App app;

    @BeforeEach
    public void setup() {
        app = new App();
        util = new TestUtil();
    }

    @Test
    @DisplayName("Should Run Basic Ray Trace")
    public void Run() {
        app.run();
    }

    @Test
    @DisplayName("should calculate colour using Ray Function")
    public void rayColorTest() {
        for (int tests = 0; tests < 2000; tests++) {
            // Generate random Origin
            Vec3 Origin = new Vec3(util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH));

            // Generate random Direction
            Vec3 Direction = new Vec3(util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH));

            Ray testRay = new Ray(Origin, Direction);
            Vec3 unitDirection = Vec3.normalize(testRay.direction());
            float t = (float) (0.5 * unitDirection.y) + 1.0f;
            Vec3 value = ((new Vec3(1.0f, 1.0f, 1.0f).scale(1.0f - t))
                    .add(new Vec3(0.5f, 0.7f, 1.0f).scale(t)));


            Vec3 result = new App().rayColor(new Ray(Origin, Direction));
            assertEquals(value, result);

        }
    }

    @Test
    @DisplayName("should calculate colour using Ray Function with zero vector")
    public void rayColorTestZeroVector() {
        Vec3 origin = new Vec3(0, 0, 0);
        Vec3 direction = new Vec3(0, 0, 0);

        Ray testRay = new Ray(origin, direction);

        Vec3 color = new App().rayColor(testRay);

        assertFalse(Float.isNaN(color.x));

    }

    @Test
    @DisplayName("should Call Intersect with Ray that hits and return true")
    public void RayIntersectSphere() {
        boolean hit;

        for (int tests = 0; tests < 2000; tests++) {
            Vec3 sphereOrigin = new Vec3(
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH), // X
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH), // Y
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH)); // Z
            float radius = 0.5f;

            Ray r = new Ray(new Vec3(sphereOrigin.x, sphereOrigin.y, 0),
                    new Vec3(0, 0, sphereOrigin.z));

            hit = app.hitSphere(sphereOrigin, radius, r);
            assertTrue(hit);
        }
    }

    @Test
    @DisplayName("should Call Intersect with Ray that misses and return false")
    public void RayIntersectSphere2() {
        boolean hit;
        int hits = 0;
        int misses = 0;
        for (int tests = 0; tests < 2000; tests++) {
            Vec3 sphereOrigin = new Vec3(
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH), // X
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH), // Y
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH)); // Z
            float radius = 0.5f;

            Ray r = new Ray(new Vec3(sphereOrigin.x, sphereOrigin.y, 0),
                    new Vec3(0, sphereOrigin.y + 200, sphereOrigin.z + 200));


            hit = app.hitSphere(sphereOrigin, radius, r);

            // Calculate hits
             float centerOfSphere = Vec3.dot(sphereOrigin, sphereOrigin);
            float a = Vec3.dot(r.direction(), r.direction());
            float b = 2.0f * Vec3.dot(sphereOrigin, r.direction());
            float c = Vec3.dot(sphereOrigin, sphereOrigin)-(radius * radius);

            float discriminant  = b*b - 4 * a * c;
           assertFalse(hit,"Should not have hit sphere\n");
           assertFalse(discriminant >0,"Discriminant Should not be more than 0");
        }
    }

    public void assertRightAngle(Vec3 vec1, Vec3 vec2,String message) {
        assertTrue(Math.abs(Vec3.dot(vec1, vec2)) < 0.000001f,message);
    }

    public void assertAngleTrue(Vec3 a,Vec3 b,float radians,String Message){
        assertEquals(radians,Vec3.angle(a, b),Message);
    }
}


