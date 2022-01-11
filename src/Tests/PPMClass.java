package Tests;

import com.MB.PPM;
import com.MB.Vec3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PPMClass {

    @Test
    @DisplayName("Should translate Vector to RGB")
    public void vectorToRGB() {
        Vec3 vec3 = new Vec3(0,0,1);

        assertEquals(PPM.vectorToRGB(vec3,1),"0 0 255");

        vec3 = new Vec3(1,0,0);

        assertEquals(PPM.vectorToRGB(vec3,1),"255 0 0");
    }
}
