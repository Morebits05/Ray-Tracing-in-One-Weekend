package Unit_Tests;

import com.MB.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomTest {

    @Test
    @DisplayName("randomFloat should not a give -0")
    public void randomFloatTest(){
        float testFloat = Utils.randomFloat(-1,1);

        assertTrue(testFloat > 0.0,"Should be More than 0");
        assertTrue(testFloat < 1.0,"Should be Less than 1");
    }
}
