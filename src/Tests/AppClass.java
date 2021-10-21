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


    TestUtil util;
    App app;
    private boolean _normalized = true;

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

            float t = (0.5f * unitDirection.y) + 1.0f;
            final Vec3 a = new Vec3(1.0f, 1.0f, 1.0f);
            final Vec3 b = new Vec3(0.5f, 0.7f, 1.0f);
            Vec3 value = Vec3.lerp(a, b, t);


            Vec3 result = new App().rayColor(new Ray(Origin, Direction));


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
    @DisplayName("should Calculate Angle on Sphere and Detect A Hit")
    public void rayIntersectSphere() {


        for (int tests = 0; tests < 200; tests++) {

            // Set Location and Radius of Sphere.
            float x;
            float y;
            float z;
            x = util.getFloatInRange(-10, 10);
            y = util.getFloatInRange(-10, 10);
            z = util.getFloatInRange(-10, 10);
            Vec3 sphereOrigin = new Vec3(x, y, z);
            float radius = util.getFloatInRange(0.1f, 2.0f);

            // To Set Up Coords for The Ray Location
            // We need Coordinates based on the radius.
            // If you don't understand why, ask Tesseract.
            x = util.getFloatInRange(-radius, radius);
            y = util.getFloatInRange(-radius, radius);
            z = util.getFloatInRange(-radius, radius);


            // Create a new Starting Point for the Ray
            Vec3 startingPoint = new Vec3();
            // Set the starting point to centre of the sphere.
            startingPoint.set(sphereOrigin);

            // Now we want a random normalized origin, so create a vector for that.
            Vec3 scalar = new Vec3(x, y, z);
            scalar = Vec3.normalize(scalar);

            // Multiply the starting point by the scalar to add noise.
            startingPoint = startingPoint.scale(scalar);


            // Get a Random Direction
            x = util.getFloatInRange(-radius, radius);
            y = util.getFloatInRange(-radius, radius);
            z = util.getFloatInRange(-radius, radius);
            scalar = new Vec3(x, y, z);
            scalar = Vec3.normalize(scalar);

            // Multiply by Random Direction to add noise
            Vec3 newDirection = startingPoint.scale(scalar);

            // Scale it by a randomized float using the radius.
            Ray r = new Ray(startingPoint,
                    newDirection);

            // Do Angle Calculation for Maximum Angle.
            Vec3 diff = r.origin.subtract(sphereOrigin);

            //System.err.println("Calculating Theta Max");
            float thetaMax = (float) Math.asin(radius / diff.length());

            // Calculate Angle of Ray
            //System.err.println("Calculating Theta");
            float theta = Vec3.angle(r.direction, sphereOrigin);
            float dp = Vec3.dot(r.direction, sphereOrigin);

            //assertEquals(true, theta <= thetaMax, "theta was not >= thetaMax");

            // Is the Ray inside the sphere
            if (diff.length() <= radius) {
                assertTrue(app.hitSphere(sphereOrigin, radius, r));
            }
            boolean testVar = app.hitSphere(sphereOrigin, radius, r);
            assertEquals(app.hitSphere(sphereOrigin, radius, r), theta <= thetaMax,
                    "theta (in degrees) was " + theta + " & thetaMax (in degrees) was " + thetaMax;

            // assertEquals(0, dp, "Ray is not orthogonal to angle");
            //assertFalse(theta <= thetaMax, "theta is less than or equal to thetaMax");
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


