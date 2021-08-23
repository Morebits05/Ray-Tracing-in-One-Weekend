package Tests;

import com.MB.Ray;
import com.MB.Vec3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class RayClass {
    private Random gen;
    @BeforeEach
    public void Before(){
        gen = new Random();
    }

    public float getFloatInRange(int high, int low){
        return gen.nextFloat()*( high- low)+ low;
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
    public void constructorTest(){
        for (int tests = 0; tests < 2000;tests++ ) {

            Vec3 v1 = new Vec3(getFloatInRange(-100,100), getFloatInRange(-100,100), getFloatInRange(-100,100));
            Vec3 v2 = new Vec3(getFloatInRange(-100,100), getFloatInRange(-100,100), getFloatInRange(-100,100));

            Ray ray = new Ray(v1, v2);

            assertEquals(v1.x, ray.origin.x);
            assertEquals(v2.z, ray.direction.z);
        }
    }

    @Test
    @DisplayName("Set Method sets origin and Direction")
    public void setMethodTest(){

        for (int tests = 0; tests < 2000; tests++) {
            Vec3 v1 = new Vec3(getFloatInRange(-100,100),
                                getFloatInRange(-100,100),
                                getFloatInRange(-100,100));

            Vec3 v2 = new Vec3(getFloatInRange(-100,100),
                    getFloatInRange(-100,100),
                    getFloatInRange(-100,100));

            Ray ray = new Ray(v1, v2);

            // Is set called on ray
            v2.set(new Vec3(getFloatInRange(-100,100),getFloatInRange(-100,100),getFloatInRange(-100,100)));
            ray.set(v2,false);
            assertEquals(v2.z,ray.direction.z);


            v2.set(new Vec3(getFloatInRange(-100,100),getFloatInRange(-100,100),getFloatInRange(-100,100)));
            ray.set(v2,true);
            assertEquals(v2.z,ray.origin.z);
        }
    }
}
