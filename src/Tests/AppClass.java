package Tests;

import com.MB.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



// In The Test Framework
// The Layout read like
// Sentences.
// Eg. AppClass
//      - Should Run Basic Ray Trace

public class AppClass {


    public Vec3 sphereOrigin;
    TestUtil util; // Utility Class for Random Generator.
    App app; // The App to Test.

    /**
     * Set up the base Variables before each test
     */
    @BeforeEach
    public void setup() {
        app = new App();
        util = new TestUtil();

        sphereOrigin = new Vec3();
    }

    /** Test The Basic App Function */
    @Test
    @DisplayName("Should Run Basic Ray Trace")
    public void Run() {
        app.run();
    }

    /**
     * Tests the RayColor Function using random co-ordinates
     */
    @Test
    @DisplayName("should calculate colour using RayColour Function")
    public void rayColorTest() {
        for (int tests = 0; tests < 2000; tests++) {
            // Generate random Origin
            HitTableList world = new HitTableList();

            Vec3 sphereOrigin = util.getSphereOrigin(TestUtil.LOW, TestUtil.HIGH);
            world.add(new Sphere(sphereOrigin,.5f));
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


           Vec3 result = new App().rayColor(new Ray(sphereOrigin, direction),world);
           boolean worldHit = world.hit(testRay,0, Utils.Constants.infinity,new HitRecord());

           assertTrue(worldHit);
        }
    }


    /**
     * Tests Whether RayColour returns NaN, aka non-normalized.
     */
    @Test
    @DisplayName("should calculate colour using Ray Function with zero vector")
    public void rayColorTestZeroVector() {
        Vec3 origin = new Vec3(0, 0, 0);
        Vec3 direction = new Vec3(0, 0, 0);

        Ray testRay = new Ray(origin, direction);

        Vec3 color = new App().rayColor(testRay,new HitTableList());

        assertFalse(Float.isNaN(color.x));

    }

    /** Tests Whether the Ray Misses Sphere when the Ray Originates from the Sphere
     *  And Points Away from the sphere.
     */
    @Test
    @DisplayName("should Call HitSphere and the Ray misses the Sphere")
    public void rayMissesSphereWhenBehindRayOrigin() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 1);
        Vec3 rayOrigin = new Vec3(2, 5, 0);
        Vec3 rayDirection = new Vec3(0, 0, -1).normalize();
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 0.5f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertEquals(theta <= thetaMax,app.hitSphere(sphereOrigin, radius, r) <0 );
        //assertEquals(theta <= thetaPiMax,app.hitSphere(sphereOrigin, radius, r)<0);
    }


    /** Tests Whether the Ray Misses Sphere when the Ray Originates In Front of the Sphere
     *  And Points Away from the sphere.
     */
    @Test
    @DisplayName("should Call HitSphere and the Ray misses the Sphere when in front of Sphere")
    public void rayMissesSphereWhenInFrontOfRayOrigin() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 0);
        Vec3 rayOrigin = new Vec3(2, 5, -1);
        Vec3 rayDirection = new Vec3(0, 0, -1).normalize();
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 0.5f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a hit
       assertEquals( theta <= thetaMax,app.hitSphere(sphereOrigin, radius, r) <0, "Theta was not <= Theta Max");
    }


    /** Tests Whether the Ray Misses Sphere when the Ray Originates from the Right of the Sphere
     *  And Points Away from the sphere.
     */
    @Test
    @DisplayName("should Call HitSphere and the Ray misses the Sphere when on right of Sphere")
    public void rayMissesSphereWhenOnRightOfSphere() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 0);
        Vec3 rayOrigin = new Vec3(3, 5, 0);
        Vec3 rayDirection = new Vec3(1, 0, 0);
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 0.5f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertEquals(theta <= thetaMax,app.hitSphere(sphereOrigin, radius, r) < 0 );
    }

    /** Tests Whether the Ray Misses Sphere when the Ray Originates from the left of the Sphere
     *  And Points Away from the sphere.
     */
    @Test
    @DisplayName("should Call HitSphere and the Ray misses the Sphere when on left of Sphere")
    public void rayMissesSphereWhenOnLeftOfSphere() {
        // Set up Sphere behind Ray Origin
        sphereOrigin = new Vec3(2, 5, 0);
        Vec3 rayOrigin = new Vec3(1, 5, 0);
        Vec3 rayDirection = new Vec3(-1, 0, 0);
        Ray r = new Ray(rayOrigin, rayDirection);
        float radius = 0.5f;

        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180f - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertEquals(theta <= thetaMax,app.hitSphere(sphereOrigin, radius, r) < 0 );
    }

    @Test
    @DisplayName("Should Return -1 when Discriminant is <0 otherwise return (-b - sqrt(discriminant) ) / (2.0*a)")
    public void HitSphereTest(){
            Vec3 sphereOrigin = new Vec3(0,3,1);
            Vec3 rayOrigin = new Vec3(0,0,0);
            Vec3 rayDirection = new Vec3(0,0,1);
            Ray r = new Ray(rayOrigin,rayDirection);
            assertTrue(app.hitSphere(sphereOrigin,.5f,r) < 0);
    }

    @Test
    @DisplayName("Should Not  Return -1 when Discriminant is not <0")
    public void HitSphereTest2(){
        Vec3 sphereOrigin = new Vec3(0,0,1);
        Vec3 rayOrigin = new Vec3(0,0,0);
        Vec3 rayDirection = new Vec3(0,0,1);
        Ray r = new Ray(rayOrigin,rayDirection);
        assertFalse(app.hitSphere(sphereOrigin,.5f,r) < 0);
    }
    @Test
    @DisplayName("should Call Ray Colour and Should Not Return Black")
    public void shouldHitSphere() {
        // Set expected Color to Black
        Vec3 expectedColour = new Vec3(0, 0, 0);
        // Set up Ray
        Ray ray = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, -1));

        // Call Function and Check Colour
        Vec3 actual = app.rayColor(ray,new HitTableList());
        assertNotEquals(expectedColour, actual);
    }

    /** Specialized Assert */
    public void assertRightAngle(final Vec3 vec1, final Vec3 vec2, final String message) {
        assertTrue(Math.abs(Vec3.dot(vec1, vec2)) < 0.000001f, message);
    }

    /** Specialized Assert */
    public void assertAngleTrue(final Vec3 a, final Vec3 b, final float radians, final String Message) {
        assertEquals(radians, Vec3.angle(a, b), Message);
    }
    /** Specialized Assert */
    public final Vec3 createNormalizedRay(final float x, final float y, final float z) {
        return Vec3.normalize(new Vec3(x, y, z));
    }
    /** Specialized Assert */
    public void assertEdgeCaseN(int theta, int thetaMax, int caseNo) {
        assertTrue(theta < thetaMax, "Edge Case " + caseNo);
    }
}



