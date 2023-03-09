package Unit_Tests;

import com.MB.*;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;



// In The Test Framework
// The Layout read like
// Sentences.
// E.g. AppClass
//      - Should Run Basic Ray Trace

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppClass {


    public Vec3 sphereOrigin;
    TestUtil util; // Utility Class for Random Generator.
    App app; // The App to Test.
    HitTableList world;
    /**
     * Set up the base Variables before each test
     */
    @BeforeEach
    public void setup() {
        app = new App();
        util = new TestUtil();

        sphereOrigin = new Vec3();
        world = new HitTableList();
    }

    /** Test The Basic App Function */
    @Test
    @DisplayName("Should Run Basic Ray Trace")
    @Order(1)
    public void Run() {
        App app = new App();
        app.run();
     }

    /**
     * Tests the RayColor Function using random co-ordinates
     */
    @Test
    @DisplayName("should calculate colour using RayColour Function")
    public void rayColorTest() {
            // Generate random Origin
            HitTableList world = new HitTableList();

            Vec3 sphereOrigin = util.getSphereOrigin(TestUtil.LOW, TestUtil.HIGH);
           // world.add(new Sphere(sphereOrigin,0.5F));
            // Generate random Direction
            Vec3 direction = new Vec3(util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH),
                    util.getFloatInRange(TestUtil.LOW, TestUtil.HIGH));

            Ray testRay = new Ray(sphereOrigin, direction);
            Vec3 unitDirection = Vec3.normalize(testRay.direction());

            float t = (float) 0.5 * (unitDirection.y + 1.0F);
            final Vec3 a = new Vec3(1.0F, 1.0F, 1.0F);
            final Vec3 b = new Vec3(0.5F, 0.7F, 1.0F);
            Vec3 value = Vec3.lerp(a, b, t);


           Vec3 result = new App().rayColor(new Ray(sphereOrigin, direction),world,1);
           boolean worldHit = world.hit(testRay,0, Utils.Constants.infinity,new HitRecord());

           assertFalse(worldHit);
           assertEquals(value,result);
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

        Vec3 color = new App().rayColor(testRay,new HitTableList(),0);

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
        float radius = 0.5F;
        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180F - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertTrue(theta <= thetaMax);
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
        float thetaPiMax = 180F - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a hit
       assertTrue( theta <= thetaMax , "Theta was not <= Theta Max");
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
        float radius = 0.5F;
        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180F - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertTrue(theta <= thetaMax);
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
        world.add(new Sphere(sphereOrigin,radius));
        // Set up Calculations
        Vec3 diff = r.origin.subtract(sphereOrigin);

        float thetaMax = (float) Math.asin(radius / diff.length());
        float thetaPiMax = 180F - thetaMax;

        float theta = Vec3.angle(rayOrigin, sphereOrigin);

        // Do we have a miss
        assertTrue(theta <= thetaMax);
    }

    @Test
    @DisplayName("Should Return -1 when Discriminant is <0 otherwise return (-b - sqrt(discriminant) ) / (2.0*a)")
    public void HitSphereTest(){
            Vec3 sphereOrigin = new Vec3(0,3,1);
            Vec3 rayOrigin = new Vec3(0,0,0);
            Vec3 rayDirection = new Vec3(0,0,1);
            world.add(new Sphere(sphereOrigin,.5f));
            Ray r = new Ray(rayOrigin,rayDirection);
            assertFalse(world.hit(r,0,0,new HitRecord()));
    }

    @Test
    @DisplayName("Should Not  Return -1 when Discriminant is not <0")
    public void HitSphereTest2(){
        Vec3 sphereOrigin = new Vec3(0,0,1);
        Vec3 rayOrigin = new Vec3(0,0,0);
        Vec3 rayDirection = new Vec3(0,0,1);
        world.add(new Sphere(sphereOrigin,.5f));

        Ray r = new Ray(rayOrigin,rayDirection);
        assertFalse(world.hit(r,0,0,new HitRecord()));
    }

    /**
     * This Test tests if rayColour doesn't return Black when depth is > 0
     * **/
    @Test
    @DisplayName("should Call Ray Colour and Should Not Return Black")
    public void shouldHitSphere() {
        // Set expected Color to Black
        Vec3 expectedColour = new Vec3(0, 0, 0);
        // Set up Ray
        Ray ray = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, -1));

        // Call Function and Check Colour
        Vec3 actual = app.rayColor(ray,new HitTableList(),1);
        assertNotEquals(expectedColour, actual);
    }


    /**
     * assertRightAngle
     * Asserts that the angle between the two vectors are
     * a right angle using the dot product.
     * @param vec1 First Vector
     * @param vec2 Second Vector
     * @param message message to print : optional
     */
    public void assertRightAngle(final Vec3 vec1, final Vec3 vec2, final String message) {
        assertTrue(Math.abs(Vec3.dot(vec1, vec2)) < 0.000001f, message);
    }

    /**
     * assertAngleTrue
     * Asserts that the angle between the two vectors are
     * equal to the parameter radians.
     * @param a First Vector
     * @param b Second Vector
     * @param radians the angle to assert against
     * @param Message message to print : optional
     */
    public void assertAngleTrue(final Vec3 a, final Vec3 b, final float radians, final String Message) {
        assertEquals(radians, Vec3.angle(a, b), Message);
    }

    /** Creates a Normalized Ray */
    public final Vec3 createNormalizedRay(final float x, final float y, final float z) {
        return Vec3.normalize(new Vec3(x, y, z));
    }

    @Test
    public void appShould_WhenRun_DrawPicture(){
        App app = new App();
        app.run();

        File picture = new File("image.ppm");
        assertTrue(picture.exists());
    }

    private Vec3 CalculateScatter(Sphere s, Ray r,HitRecord rec,Vec3 target,Vec3 random){

        HitRecord tempRec = new HitRecord();
        if (s.hit(r,0,Float.POSITIVE_INFINITY,tempRec)) {
            target.set(tempRec.point
                    .add(tempRec.normal)
                    .add(random));
            return CalculateColor(target);
        }
        return new Vec3(0,0,0);
    }

    private Vec3 CalculateColor(Vec3 color){
        return color.scale(0.5f);
    }



}



