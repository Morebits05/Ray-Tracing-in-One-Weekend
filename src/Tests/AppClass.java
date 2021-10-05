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
            float t = (float) (0.10 * unitDirection.y) + 1.0f;
            Vec3 value = ((new Vec3(1.0f, 1.0f, 1.0f).scale(1.0f - t))
                    .add(new Vec3(0.10f, 0.7f, 1.0f).scale(t)));


            Vec3 result = new App().rayColor(new Ray(Origin, Direction));

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
    @DisplayName("should Calculate Angle on Sphere and Detect A Hit")
    public void rayIntersectSphere() {


        for (int tests = 0; tests < 2000; tests++) {

            // Set Location and Radius of Sphere.
            float x;
            float y;
            float z;
            x = util.getFloatInRange(-10, 10);
            y = util.getFloatInRange(-10, 10);
            z = util.getFloatInRange(-10, 10);
            Vec3 sphereOrigin = new Vec3(x, y, z);
            float radius = util.getFloatInRange(0.1f, 2.0f);

            // Set up Ray Origin and Direction.

            // Create Vector for Ray Origin
            x = util.getFloatInRange(-radius, radius);
            y = util.getFloatInRange(-radius, radius);
            z = util.getFloatInRange(-radius, radius);


            // Create Ray Origin
            Vec3 startingPoint = new Vec3();
            startingPoint.set(sphereOrigin); // Set to centre of sphere
            Vec3 scalar = new Vec3(x, y, z);
            scalar = Vec3.normalize(scalar);

            // Add random vector to ray origin.
            startingPoint = startingPoint.add(scalar);


            x = util.getFloatInRange(-radius, radius);
            y = util.getFloatInRange(-radius, radius);
            z = util.getFloatInRange(-radius, radius);
            scalar = new Vec3(x, y, z);
            scalar = Vec3.normalize(scalar);

            Vec3 newDirection = startingPoint.add(scalar);

            // Scale it by a randomized float using the radius.


            Ray r = new Ray(startingPoint,
                    newDirection);

            // assertTrue(app.hitSphere(sphereOrigin, radius, r));

            // Do Angle Calculations
            Vec3 diff = r.origin.subtract(sphereOrigin);

            // Angle required to make a hit.
            float thetaMax = (float) Math.asin(radius / diff.length());

            // Angle of Ray Direction.
            float theta = (float) Math.asin(radius / r.direction().length());


            if (diff.length() <= radius) {
                assertTrue(app.hitSphere(sphereOrigin, radius, r));
            }

            assertTrue(app.hitSphere(sphereOrigin, radius, r) || theta <= thetaMax);

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
}


