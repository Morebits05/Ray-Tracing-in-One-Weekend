package Unit_Tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Camera {
    @Test
    @DisplayName("Class should have constructor")
    public void constructorTest(){
        assertEquals(1, Camera.class.getConstructors().length);
    }


}
