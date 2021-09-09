package Tests;

import com.MB.App;
import com.MB.Ray;
import com.MB.Vec3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppClass {

    TestUtil util = new TestUtil();

    @Test
    @DisplayName("Should Run Basic Ray Trace")
    public void Run() {
        App app = new App();
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
            Vec3 origin = new Vec3(0,0,0);
            Vec3 direction = new Vec3(0,0,0);

            Ray testRay = new Ray(origin, direction);

            Vec3 color = new App().rayColor(testRay);

        assertFalse(Float.isNaN(color.x));

    }




}
