package Tests;

import com.MB.App;
import com.MB.Ray;
import com.MB.Vec3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppClass {

    Random rand;
    @BeforeEach
    public void Before(){
        rand = new Random();
    }

    @Test
    @DisplayName("Should Run Basic Ray Trace")
    public void Run(){
        App app = new App();
        app.run();
    }

    @Test
    @DisplayName("should calculate colour using Ray Function")
    public void rayColorTest(){
        for (int tests = 0; tests < 2000; tests++) {
            Vec3 Origin = new Vec3(rand.nextFloat() * 100, rand.nextFloat() * 100, rand.nextFloat() * 100);
            Vec3 Direction = new Vec3(rand.nextFloat() * 100, rand.nextFloat() * 100, rand.nextFloat() * 100);


            Ray testRay = new Ray(Origin, Direction);
            Vec3 unitDirection = Vec3.normalize(testRay.direction());
            float t = (float) (0.5 * unitDirection.y) + 1.0f;
            Vec3 value = new Vec3(1.0f, 1.0f, 1.0f).scale(1.0f - t).add(
                    new Vec3(0.5f, 0.7f, 1.0f).scale(t));


            Vec3 result = new App().rayColor(new Ray(Origin, Direction));
            assertEquals(value, result);

        }
    }





}
