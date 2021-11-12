package Tests;

import com.MB.App;
import com.MB.Ray;
import com.MB.Vec3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class AppClass {


    public Vec3 sphereOrigin;
    TestUtil util;
    App app;
    private boolean _normalized = true;

    @BeforeEach
    public void setup() {
        app = new App();
        util = new TestUtil();

        sphereOrigin = new Vec3();
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
            Vec3 sphereOrigin = util.getSphereOrigin(TestUtil.LOW, TestUtil.HIGH);

            // Generate random Direction
            Vec3 direction = new Vec3(util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH));

            Ray testRay = new Ray(sphereOrigin, direction);
            Vec3 unitDirection = Vec3.normalize(testRay.direction());

            float t = (0.5f * unitDirection.y) + 1.0f;
            final Vec3 a = new Vec3(1.0f, 1.0f, 1.0f);
            final Vec3 b = new Vec3(0.5f, 0.7f, 1.0f);
            Vec3 value = Vec3.lerp(a, b, t);


            Vec3 result = new App().rayColor(new Ray(sphereOrigin, direction));


            assertThat(value, is(result));
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
    @DisplayName("should Call HitSphere and the Ray misses the Sphere")
    public void rayMissesSphereWhenBehindRayOrigin() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 1);
        Vec3 rayOrigin = new Vec3(2, 5, 0);
        Vec3 rayDirection = new Vec3(0, 0, -1).normalize();
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 2.0f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaMax);
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaPiMax);
    }


    @Test
    @DisplayName("should Call HitSphere and the Ray misses the Sphere when in front of Sphere")
    public void rayMissesSphereWhenInFrontOfRayOrigin() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 0);
        Vec3 rayOrigin = new Vec3(2, 5, -1);
        Vec3 rayDirection = new Vec3(0, 0, -1).normalize();
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 2.0f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaMax, "Not Hit!");
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaPiMax);
    }


    @Test
    @DisplayName("should Call HitSphere and the Ray misses the Sphere when on right of Sphere")
    public void rayMissesSphereWhenOnRightOfSphere() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 0);
        Vec3 rayOrigin = new Vec3(3, 5, 0);
        Vec3 rayDirection = new Vec3(1, 0, 0).normalize();
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 2.0f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaMax);
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaPiMax);
    }


    @Test
    @DisplayName("should Call HitSphere and the Ray misses the Sphere when on left of Sphere")
    public void rayMissesSphereWhenOnLeftOfSphere() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 0);
        Vec3 rayOrigin = new Vec3(1, 5, 0);
        Vec3 rayDirection = new Vec3(-1, 0, 0).normalize();
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 2.0f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaMax);
        assertNotEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaPiMax);
    }


    @Test
    @DisplayName("should Calculate Angle on Sphere and Detect A Hit")
    public void rayIntersectSphere() {


        for (int tests = 0; tests < 200; tests++) {

            // Set Location and Radius of Sphere.
            sphereOrigin = util.getSphereOrigin(-10f, 10f);


            float radius = util.getRandomRadius(0.1f, 8.0f);


            // Create a origin and direction for the ray
            Vec3 rayOrigin = util.getRandomPointOnUnitSphere();
            Vec3 rayDirection = util.getRandomPointOnUnitSphere();


            // Set up the Ray
            Ray r = new Ray(rayOrigin,
                    rayDirection);

            // Do Angle Calculation for Maximum Angle needed to hit (in radians).
            Vec3 diff = r.origin.subtract(sphereOrigin);
            float thetaMax = (float) Math.asin(radius / diff.length());
            float thetaPiMax = (float) 180.0f - thetaMax;
            //Calculate the actual angle of the ray (in radians).
            float theta = Vec3.angle(r.direction, sphereOrigin);

            // Do we get a hit
            assertEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaMax," Not Less Than theta Max");
            assertEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaPiMax, "Not Less than theta Pi Max");
        }


    }


    @Test
    @DisplayName("Should Hit Sphere and return Red")
    public void shouldHitSphere() {
        // Sphere should be Red
        Vec3 expectedColour = new Vec3(1, 0, 0);
        Ray ray = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, -1));
        Vec3 actual = app.rayColor(ray);
        assertEquals(expectedColour, actual);
    }

    public void assertRightAngle(final Vec3 vec1, final Vec3 vec2, final String message) {
        assertTrue(Math.abs(Vec3.dot(vec1, vec2)) < 0.000001f, message);
    }

    public void assertAngleTrue(final Vec3 a, final Vec3 b, final float radians, final String Message) {
        assertEquals(radians, Vec3.angle(a, b), Message);
    }

    public final Vec3 createNormalizedRay(final float x, final float y, final float z) {
        return Vec3.normalize(new Vec3(x, y, z));
    }

    public void assertEdgeCaseN(int theta, int thetaMax, int caseNo) {
        assertTrue(theta < thetaMax, "Edge Case " + caseNo);
    }
}



